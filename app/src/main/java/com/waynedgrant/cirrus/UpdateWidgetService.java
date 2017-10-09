/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.waynedgrant.cirrus.clientraw.ClientRaw;
import com.waynedgrant.cirrus.clientraw.ClientRawCache;
import com.waynedgrant.cirrus.clientraw.ClientRawRequest;
import com.waynedgrant.cirrus.clientraw.ClientRawResponse;
import com.waynedgrant.cirrus.clientraw.ClientRawUrl;
import com.waynedgrant.cirrus.clientraw.RetrieveClientRawTask;
import com.waynedgrant.cirrus.measures.Temperature;
import com.waynedgrant.cirrus.measures.Trend;
import com.waynedgrant.cirrus.measures.WeatherItem;
import com.waynedgrant.cirrus.preferences.Preferences;
import com.waynedgrant.cirrus.presentation.colorizers.TemperatureColorizer;
import com.waynedgrant.cirrus.presentation.colorizers.WeatherItemColorizer;
import com.waynedgrant.cirrus.presentation.formatters.DateFormat;
import com.waynedgrant.cirrus.presentation.formatters.DateFormatter;
import com.waynedgrant.cirrus.presentation.formatters.FormattedWeatherItem;
import com.waynedgrant.cirrus.presentation.formatters.StringFormatter;
import com.waynedgrant.cirrus.presentation.formatters.TemperatureFormatter;
import com.waynedgrant.cirrus.presentation.formatters.TimeFormat;
import com.waynedgrant.cirrus.presentation.formatters.TimeFormatter;
import com.waynedgrant.cirrus.presentation.formatters.TrendFormatter;
import com.waynedgrant.cirrus.presentation.formatters.WeatherItemFormatter;
import com.waynedgrant.cirrus.units.PressureUnit;
import com.waynedgrant.cirrus.units.RainfallUnit;
import com.waynedgrant.cirrus.units.TemperatureUnit;
import com.waynedgrant.cirrus.units.WindDirectionUnit;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

import java.util.List;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.waynedgrant.cirrus.R.drawable.refreshing;
import static com.waynedgrant.cirrus.R.drawable.widget_shape_opaque;
import static com.waynedgrant.cirrus.R.drawable.widget_shape_transparent;
import static com.waynedgrant.cirrus.R.id.configButton;
import static com.waynedgrant.cirrus.R.id.dailyMaxOutdoorTemperature;
import static com.waynedgrant.cirrus.R.id.dailyMinOutdoorTemperature;
import static com.waynedgrant.cirrus.R.id.mainWidgetLayout;
import static com.waynedgrant.cirrus.R.id.outdoorTemperature;
import static com.waynedgrant.cirrus.R.id.outdoorTemperatureTrend;
import static com.waynedgrant.cirrus.R.id.stationName;
import static com.waynedgrant.cirrus.R.id.statusImage;
import static com.waynedgrant.cirrus.R.id.weatherItemLabel1;
import static com.waynedgrant.cirrus.R.id.weatherItemLabel2;
import static com.waynedgrant.cirrus.R.id.weatherItemLabel3;
import static com.waynedgrant.cirrus.R.id.weatherItemLabel4;
import static com.waynedgrant.cirrus.R.id.weatherItemLabel5;
import static com.waynedgrant.cirrus.R.id.weatherItemValue1;
import static com.waynedgrant.cirrus.R.id.weatherItemValue2;
import static com.waynedgrant.cirrus.R.id.weatherItemValue3;
import static com.waynedgrant.cirrus.R.id.weatherItemValue4;
import static com.waynedgrant.cirrus.R.id.weatherItemValue5;
import static com.waynedgrant.cirrus.R.id.whenRefreshed;
import static com.waynedgrant.cirrus.R.layout.widget_layout;
import static com.waynedgrant.cirrus.WidgetProvider.ERROR_ACTION;
import static com.waynedgrant.cirrus.WidgetProvider.ERROR_MESSAGE_EXTRA;
import static com.waynedgrant.cirrus.WidgetProvider.FETCH_FRESH_CLIENT_RAW;

public class UpdateWidgetService extends Service
{
    private static final String TAG = "UpdateWidgetService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "onStartCommand()");

        int[] appWidgetIds = intent.getIntArrayExtra(EXTRA_APPWIDGET_IDS);

        if (appWidgetIds != null)
        {
            Context context = getApplicationContext();
            Preferences preferences = new Preferences(context);

            for (int appWidgetId : appWidgetIds)
            {
                boolean configured = preferences.isConfigured(appWidgetId);

                if (configured)
                {
                    boolean fetchFreshClientRaw = intent.getBooleanExtra(FETCH_FRESH_CLIENT_RAW, true);

                    if (!fetchFreshClientRaw && ClientRawCache.isCached(appWidgetId))
                    {
                        updateDisplayWithCachedClientRaw(appWidgetId, preferences);
                    }
                    else
                    {
                        requestUpdatedClientRaw(appWidgetId, preferences);
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void requestUpdatedClientRaw(int appWidgetId, Preferences preferences)
    {
        Log.d(TAG, "requestUpdatedClientRaw()");

        String clientRawUrl = preferences.getClientRawUrl(appWidgetId);
        int connectTimeoutMs = preferences.getConnectionTimeout(appWidgetId).getTimeoutMsecs();
        int readTimeoutMs = preferences.getReadTimeout(appWidgetId).getTimeoutMsecs();

        RetrieveClientRawTask retrieveClientRawTask = new RetrieveClientRawTask(this, connectTimeoutMs, readTimeoutMs);
        retrieveClientRawTask.execute(new ClientRawRequest(appWidgetId, new ClientRawUrl(clientRawUrl)));

        RemoteViews remoteViews = getRemoteViews();

        updateStatusToDisplayRefreshing(remoteViews);
        updateBackground(appWidgetId, remoteViews, preferences);
        updateIntents(appWidgetId, remoteViews);

        pushUpdatesToWidget(appWidgetId, remoteViews);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind()");

        return null;
    }

    private void updateDisplayWithCachedClientRaw(int appWidgetId, Preferences preferences)
    {
        Log.d(TAG, "updateDisplayWithCachedClientRaw()");

        RemoteViews remoteViews = getRemoteViews();

        ClientRaw clientRaw = ClientRawCache.fetch(appWidgetId);
        updateDisplay(appWidgetId, preferences, remoteViews, clientRaw);

        updateBackground(appWidgetId, remoteViews, preferences);
        updateIntents(appWidgetId, remoteViews);

        pushUpdatesToWidget(appWidgetId, remoteViews);
    }

    private void updateDisplay(int appWidgetId, Preferences preferences, RemoteViews remoteViews, ClientRaw clientRaw)
    {
        updateStationNameDisplay(appWidgetId, remoteViews, preferences, clientRaw);
        updateTemperatureDisplay(appWidgetId, remoteViews, preferences, clientRaw);
        updateWeatherItemsDisplay(appWidgetId, remoteViews, preferences, clientRaw);
        updateWhenRefreshedDisplay(appWidgetId, remoteViews, preferences, clientRaw);
        remoteViews.setViewVisibility(statusImage, INVISIBLE);
    }

    private void updateIntents(int appWidgetId, RemoteViews remoteViews)
    {
        updateClickRefreshIntent(appWidgetId, remoteViews);
        updateClickConfigIntent(appWidgetId, remoteViews);
    }

    public void handleClientRawResponses(List<ClientRawResponse> responses)
    {
        Log.d(TAG, "handleClientRawResponses()");

        Preferences preferences = new Preferences(getApplicationContext());

        for (ClientRawResponse response : responses)
        {
            handleClientRawResponse(response, preferences);
        }

        stopSelf();
    }

    private void handleClientRawResponse(ClientRawResponse response, Preferences preferences)
    {
        int appWidgetId = response.getAppWidgetId();
        RemoteViews remoteViews = getRemoteViews();

        ClientRaw clientRaw = response.getClientRaw();

        if (clientRaw == null)
        {
            String errorMessage = response.getErrorMessage();
            updateStatusToDisplayError(appWidgetId, remoteViews, errorMessage);
        }
        else
        {
            ClientRawCache.update(appWidgetId, clientRaw);
            updateDisplay(appWidgetId, preferences, remoteViews, clientRaw);
        }

        updateBackground(appWidgetId, remoteViews, preferences);
        updateIntents(appWidgetId, remoteViews);

        pushUpdatesToWidget(appWidgetId, remoteViews);
    }

    private RemoteViews getRemoteViews()
    {
        return new RemoteViews(getApplicationContext().getPackageName(), widget_layout);
    }

    private void updateBackground(int appWidgetId, RemoteViews remoteViews, Preferences preferences)
    {
        int drawable = widget_shape_opaque;

        if (preferences.isTransparent(appWidgetId))
        {
            drawable = widget_shape_transparent;
        }

        remoteViews.setInt(mainWidgetLayout, "setBackgroundResource", drawable);
    }

    private void updateStatusToDisplayRefreshing(RemoteViews remoteViews)
    {
        remoteViews.setImageViewResource(statusImage, refreshing);
        remoteViews.setViewVisibility(statusImage, VISIBLE);

        remoteViews.setOnClickPendingIntent(statusImage, null);
    }

    private void updateStatusToDisplayError(int appWidgetId, RemoteViews remoteViews, String errorMessage)
    {
        remoteViews.setImageViewResource(statusImage, R.drawable.error);
        remoteViews.setViewVisibility(statusImage, VISIBLE);

        Context context = getApplicationContext();
        Intent clickErrorIntent = new Intent(context, WidgetProvider.class);
        clickErrorIntent.setAction(ERROR_ACTION);
        clickErrorIntent.putExtra(EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
        clickErrorIntent.putExtra(ERROR_MESSAGE_EXTRA, errorMessage);
        clickErrorIntent.setData(Uri.withAppendedPath(Uri.parse("customuri://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent errorPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, clickErrorIntent, FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(statusImage, errorPendingIntent);
    }

    private void updateClickRefreshIntent(int appWidgetId, RemoteViews remoteViews)
    {
        Context context = getApplicationContext();
        Intent clickRefreshIntent = new Intent(context, WidgetProvider.class);
        clickRefreshIntent.setAction(ACTION_APPWIDGET_UPDATE);
        clickRefreshIntent.putExtra(EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
        clickRefreshIntent.setData(Uri.withAppendedPath(Uri.parse("customuri://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, clickRefreshIntent, FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(mainWidgetLayout, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(stationName, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(dailyMaxOutdoorTemperature, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(dailyMinOutdoorTemperature, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(outdoorTemperature, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(outdoorTemperatureTrend, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemLabel1, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemValue1, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemLabel2, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemValue2, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemLabel3, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemValue3, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemLabel4, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemValue4, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemLabel5, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(weatherItemValue5, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(whenRefreshed, refreshPendingIntent);
    }

    private void updateClickConfigIntent(int appWidgetId, RemoteViews remoteViews)
    {
        Context context = getApplicationContext();
        Intent clickConfigIntent = new Intent(context, WidgetConfigActivity.class);
        clickConfigIntent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        clickConfigIntent.setData(Uri.withAppendedPath(Uri.parse("customuri://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, appWidgetId, clickConfigIntent, FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(configButton, configPendingIntent);
    }

    private void updateStationNameDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        String stationNameValue = preferences.getStationName(appWidgetId);

        if (stationNameValue == null)
        {
            stationNameValue = clientRaw.getStationName();
        }

        stationNameValue = new StringFormatter(stationNameValue).format();

        remoteViews.setTextViewText(stationName, stationNameValue);
    }

    private void updateTemperatureDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        TemperatureUnit temperatureUnit = preferences.getTemperatureUnit(appWidgetId);

        Temperature dailyMaxOutdoorTemperatureValue = clientRaw.getDailyMaxOutdoorTemperature();
        Temperature dailyMinOutdoorTemperatureValue = clientRaw.getDailyMinOutdoorTemperature();
        Temperature outdoorTemperatureValue = clientRaw.getOutdoorTemperature();
        Trend outdoorTemperatureTrendValue = clientRaw.getOutdoorTemperatureTrend();

        remoteViews.setTextViewText(dailyMaxOutdoorTemperature, new TemperatureFormatter(dailyMaxOutdoorTemperatureValue).format(temperatureUnit));
        remoteViews.setTextColor(dailyMaxOutdoorTemperature, new TemperatureColorizer(dailyMaxOutdoorTemperatureValue).colorize());

        remoteViews.setTextViewText(dailyMinOutdoorTemperature, new TemperatureFormatter(dailyMinOutdoorTemperatureValue).format(temperatureUnit));
        remoteViews.setTextColor(dailyMinOutdoorTemperature, new TemperatureColorizer(clientRaw.getDailyMinOutdoorTemperature()).colorize());

        remoteViews.setTextViewText(outdoorTemperature, new TemperatureFormatter(outdoorTemperatureValue).format(temperatureUnit));
        remoteViews.setTextColor(outdoorTemperature, new TemperatureColorizer(clientRaw.getOutdoorTemperature()).colorize());

        remoteViews.setTextViewText(outdoorTemperatureTrend, new TrendFormatter(outdoorTemperatureTrendValue).format());
    }

    private void updateWeatherItemsDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        TemperatureUnit temperatureUnit = preferences.getTemperatureUnit(appWidgetId);
        PressureUnit pressureUnit = preferences.getPressureUnit(appWidgetId);
        WindSpeedUnit windSpeedUnit = preferences.getWindSpeedUnit(appWidgetId);
        WindDirectionUnit windDirectionUnit = preferences.getWindDirectionUnit(appWidgetId);
        RainfallUnit rainfallUnit = preferences.getRainfallUnit(appWidgetId);

        WeatherItemFormatter weatherItemFormatter =
                new WeatherItemFormatter(clientRaw, temperatureUnit, pressureUnit, windSpeedUnit, windDirectionUnit, rainfallUnit);

        WeatherItemColorizer weatherItemColorizer = new WeatherItemColorizer(clientRaw);

        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem1(appWidgetId), weatherItemFormatter, weatherItemColorizer, weatherItemLabel1, weatherItemValue1);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem2(appWidgetId), weatherItemFormatter, weatherItemColorizer, weatherItemLabel2, weatherItemValue2);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem3(appWidgetId), weatherItemFormatter, weatherItemColorizer, weatherItemLabel3, weatherItemValue3);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem4(appWidgetId), weatherItemFormatter, weatherItemColorizer, weatherItemLabel4, weatherItemValue4);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem5(appWidgetId), weatherItemFormatter, weatherItemColorizer, weatherItemLabel5, weatherItemValue5);
    }

    private void updateWeatherItemDisplay(RemoteViews remoteViews, WeatherItem weatherItem, WeatherItemFormatter weatherItemFormatter, WeatherItemColorizer weatherItemColorizer, int weatherItemLabelId, int weatherItemValueId)
    {
        FormattedWeatherItem formattedWeatherItem = weatherItemFormatter.format(weatherItem);

        remoteViews.setTextViewText(weatherItemLabelId, formattedWeatherItem.getLabel());
        remoteViews.setTextViewText(weatherItemValueId, formattedWeatherItem.getValue());
        remoteViews.setTextColor(weatherItemValueId, weatherItemColorizer.colorize(weatherItem));
    }

    private void updateWhenRefreshedDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        TimeFormat timeFormat = preferences.getTimeFormat(appWidgetId);
        String timeRefreshed = new TimeFormatter(clientRaw.getHour(), clientRaw.getMinute()).format(timeFormat);

        String whenRefreshedValue = timeRefreshed;

        DateFormat dateFormat = preferences.getDateFormat(appWidgetId);

        if (dateFormat != null) // Date display is optional based on whether or not a format is available
        {
            String dateRefreshed = new DateFormatter(clientRaw.getYear(), clientRaw.getMonth(), clientRaw.getDay()).format(dateFormat);
            whenRefreshedValue = dateRefreshed + " " + timeRefreshed;
        }

        remoteViews.setTextViewText(whenRefreshed, whenRefreshedValue);
    }

    private void pushUpdatesToWidget(int appWidgetId, RemoteViews remoteViews)
    {
        AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(appWidgetId, remoteViews);
    }
}
