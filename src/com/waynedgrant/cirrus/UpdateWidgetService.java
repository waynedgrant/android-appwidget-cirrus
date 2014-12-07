/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus;

import java.util.List;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;

import com.waynedgrant.cirrus.clientraw.ClientRaw;
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

public class UpdateWidgetService extends Service
{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        if (appWidgetIds != null)
        {
            Preferences preferences = new Preferences(getApplicationContext());
            
            for (int appWidgetId : appWidgetIds)
            {
                boolean configured = preferences.isConfigured(appWidgetId);
                
                if (configured)
                {
                    String clientRawUrl = preferences.getClientRawUrl(appWidgetId);
                 
                    int connectTimeoutMs = preferences.getConnectionTimeout(appWidgetId).getTimeoutMsecs();
                    int readTimoutMs = preferences.getReadTimeout(appWidgetId).getTimeoutMsecs();
                    
                    RetrieveClientRawTask retrieveClientRawTask = new RetrieveClientRawTask(this, connectTimeoutMs, readTimoutMs);
                    retrieveClientRawTask.execute(new ClientRawRequest(appWidgetId, new ClientRawUrl(clientRawUrl)));
                    
                    RemoteViews remoteViews = getRemoteViews();
                    
                    setWidgetStatusToDisplayRefreshing(remoteViews);
                    setWidgetBackground(appWidgetId, remoteViews, preferences);
                    setWidgetClickRefreshIntent(appWidgetId, remoteViews);
                    setWidgetClickConfigIntent(appWidgetId, remoteViews);
                    
                    pushUpdatesToWidget(appWidgetId, remoteViews);
                }
            }
        }
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    public void handleClientRawResponses(List<ClientRawResponse> responses)
    {
        Context context = getApplicationContext();
        
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        
        for (ClientRawResponse response : responses)
        {
            handleClientRawResponse(context, appWidgetManager, response);
        }
        
        stopSelf();
    }

    private void handleClientRawResponse(Context context, AppWidgetManager appWidgetManager, ClientRawResponse response)
    {
        ClientRaw clientRaw = response.getClientRaw();
        
        int appWidgetId = response.getAppWidgetId();
        RemoteViews remoteViews = getRemoteViews();

        Preferences preferences = new Preferences(context);
        
        if (clientRaw == null)
        {
            String errorMessage = response.getErrorMessage();
            setWidgetStatusToDisplayError(appWidgetId, remoteViews, errorMessage);
        }
        else
        {
            setWidgetStationNameDisplay(appWidgetId, remoteViews, preferences, clientRaw);
            setWidgetTemperatureDisplay(appWidgetId, remoteViews, preferences, clientRaw);
            setWidgetWeatherItemsDisplay(appWidgetId, remoteViews, preferences, clientRaw);
            setWidgetWhenRefreshedDisplay(appWidgetId, remoteViews, preferences, clientRaw);
            
            remoteViews.setViewVisibility(R.id.statusImage, View.INVISIBLE);
        }
        
        setWidgetBackground(appWidgetId, remoteViews, preferences);
        setWidgetClickRefreshIntent(appWidgetId, remoteViews);
        setWidgetClickConfigIntent(appWidgetId, remoteViews);
        
        pushUpdatesToWidget(appWidgetId, remoteViews);
    }
    
    private RemoteViews getRemoteViews()
    {
        return new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
    }
    
    private void setWidgetBackground(int appWidgetId, RemoteViews remoteViews, Preferences preferences)
    {
        int drawable;
        
        if (preferences.isTransparent(appWidgetId))
        {
            drawable = R.drawable.widget_shape_transparent;
        }
        else
        {
            drawable = R.drawable.widget_shape_opaque;
        }
        
        remoteViews.setInt(R.id.mainWidgetLayout, "setBackgroundResource", drawable);
    }
    
    private void setWidgetStatusToDisplayRefreshing(RemoteViews remoteViews)
    {
        remoteViews.setImageViewResource(R.id.statusImage, R.drawable.refreshing);
        remoteViews.setViewVisibility(R.id.statusImage, View.VISIBLE);
        
        remoteViews.setOnClickPendingIntent(R.id.statusImage, null);
    }
    
    private void setWidgetStatusToDisplayError(int appWidgetId, RemoteViews remoteViews, String errorMessage)
    {
        remoteViews.setImageViewResource(R.id.statusImage, R.drawable.error);
        remoteViews.setViewVisibility(R.id.statusImage, View.VISIBLE);
        
        Context context = getApplicationContext();
        Intent clickErrorIntent = new Intent(context, WidgetProvider.class);
        clickErrorIntent.setAction(WidgetProvider.ERROR_ACTION);
        clickErrorIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
        clickErrorIntent.putExtra(WidgetProvider.ERROR_MESSAGE_EXTRA, errorMessage);
        clickErrorIntent.setData(Uri.withAppendedPath(Uri.parse("customuri://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent errorPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, clickErrorIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.statusImage, errorPendingIntent);
    }
    
    private void setWidgetClickRefreshIntent(int appWidgetId, RemoteViews remoteViews)
    {
        Context context = getApplicationContext();
        Intent clickRefreshIntent = new Intent(context, WidgetProvider.class);
        clickRefreshIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        clickRefreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
        clickRefreshIntent.setData(Uri.withAppendedPath(Uri.parse("customuri://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, clickRefreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        remoteViews.setOnClickPendingIntent(R.id.mainWidgetLayout, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.stationName, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.dailyMaxOutdoorTemperature, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.dailyMinOutdoorTemperature, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.outdoorTemperature, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.outdoorTemperatureTrend, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemLabel1, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemValue1, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemLabel2, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemValue2, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemLabel3, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemValue3, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemLabel4, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemValue4, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemLabel5, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.weatherItemValue5, refreshPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.whenRefreshed, refreshPendingIntent);
    }
    
    private void setWidgetClickConfigIntent(int appWidgetId, RemoteViews remoteViews)
    {
        Context context = getApplicationContext();
        Intent clickConfigIntent = new Intent(context, WidgetConfigActivity.class);
        clickConfigIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        clickConfigIntent.setData(Uri.withAppendedPath(Uri.parse("customuri://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, appWidgetId, clickConfigIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        remoteViews.setOnClickPendingIntent(R.id.configButton, configPendingIntent);
    }
    
    private void setWidgetStationNameDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        String stationName = preferences.getStationName(appWidgetId);
        
        if (stationName == null)
        {
            stationName = clientRaw.getStationName();
        }
        
        stationName = new StringFormatter(stationName).format();
        
        remoteViews.setTextViewText(R.id.stationName, stationName);
    }

    private void setWidgetTemperatureDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        TemperatureUnit temperatureUnit = preferences.getTemperatureUnit(appWidgetId);
        
        Temperature dailyMaxOutdoorTemperature = clientRaw.getDailyMaxOutdoorTemperature();
        Temperature dailyMinOutdoorTemperature = clientRaw.getDailyMinOutdoorTemperature();
        Temperature outdoorTemperature = clientRaw.getOutdoorTemperature();
        Trend outdoorTemperatureTrend = clientRaw.getOutdoorTemperatureTrend();

        remoteViews.setTextViewText(R.id.dailyMaxOutdoorTemperature, new TemperatureFormatter(dailyMaxOutdoorTemperature).format(temperatureUnit));
        remoteViews.setTextColor(R.id.dailyMaxOutdoorTemperature, new TemperatureColorizer(dailyMaxOutdoorTemperature).colorize());
        
        remoteViews.setTextViewText(R.id.dailyMinOutdoorTemperature, new TemperatureFormatter(dailyMinOutdoorTemperature).format(temperatureUnit));
        remoteViews.setTextColor(R.id.dailyMinOutdoorTemperature, new TemperatureColorizer(clientRaw.getDailyMinOutdoorTemperature()).colorize());
        
        remoteViews.setTextViewText(R.id.outdoorTemperature, new TemperatureFormatter(outdoorTemperature).format(temperatureUnit));
        remoteViews.setTextColor(R.id.outdoorTemperature, new TemperatureColorizer(clientRaw.getOutdoorTemperature()).colorize());
        
        remoteViews.setTextViewText(R.id.outdoorTemperatureTrend, new TrendFormatter(outdoorTemperatureTrend).format());
    }

    private void setWidgetWeatherItemsDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        TemperatureUnit temperatureUnit = preferences.getTemperatureUnit(appWidgetId);
        PressureUnit pressureUnit = preferences.getPressureUnit(appWidgetId);
        WindSpeedUnit windSpeedUnit = preferences.getWindSpeedUnit(appWidgetId);
        WindDirectionUnit windDirectionUnit = preferences.getWindDirectionUnit(appWidgetId);
        RainfallUnit rainfallUnit = preferences.getRainfallUnit(appWidgetId);
        
        WeatherItemFormatter weatherItemFormatter =
                new WeatherItemFormatter(clientRaw, temperatureUnit, pressureUnit, windSpeedUnit, windDirectionUnit, rainfallUnit);
        
        WeatherItemColorizer weatherItemColorizer = new WeatherItemColorizer(clientRaw);
        
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem1(appWidgetId), weatherItemFormatter, weatherItemColorizer, R.id.weatherItemLabel1, R.id.weatherItemValue1);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem2(appWidgetId), weatherItemFormatter, weatherItemColorizer, R.id.weatherItemLabel2, R.id.weatherItemValue2);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem3(appWidgetId), weatherItemFormatter, weatherItemColorizer, R.id.weatherItemLabel3, R.id.weatherItemValue3);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem4(appWidgetId), weatherItemFormatter, weatherItemColorizer, R.id.weatherItemLabel4, R.id.weatherItemValue4);
        updateWeatherItemDisplay(remoteViews, preferences.getWeatherItem5(appWidgetId), weatherItemFormatter, weatherItemColorizer, R.id.weatherItemLabel5, R.id.weatherItemValue5);
    }
    
    private void updateWeatherItemDisplay(RemoteViews remoteViews, WeatherItem weatherItem, WeatherItemFormatter weatherItemFormatter, WeatherItemColorizer weatherItemColorizer, int weatherItemLabelId, int weatherItemValueId)
    {
        FormattedWeatherItem formattedWeatherItem = weatherItemFormatter.format(weatherItem);

        remoteViews.setTextViewText(weatherItemLabelId, formattedWeatherItem.getLabel());
        remoteViews.setTextViewText(weatherItemValueId, formattedWeatherItem.getValue());
        remoteViews.setTextColor(weatherItemValueId, weatherItemColorizer.colorize(weatherItem));
    }
    
    private void setWidgetWhenRefreshedDisplay(int appWidgetId, RemoteViews remoteViews, Preferences preferences, ClientRaw clientRaw)
    {
        TimeFormat timeFormat = preferences.getTimeFormat(appWidgetId);        
        String timeRefreshed = new TimeFormatter(clientRaw.getHour(), clientRaw.getMinute()).format(timeFormat);
        
        String whenRefreshed = timeRefreshed;
        
        DateFormat dateFormat = preferences.getDateFormat(appWidgetId);
        
        if (dateFormat != null) // Date display is optional based on whether or not a format is available
        {
            String dateRefreshed = new DateFormatter(clientRaw.getYear(), clientRaw.getMonth(), clientRaw.getDay()).format(dateFormat);
            whenRefreshed = dateRefreshed + " " + timeRefreshed;
        }
        
        remoteViews.setTextViewText(R.id.whenRefreshed, whenRefreshed);
    }
    
    private void pushUpdatesToWidget(int appWidgetId, RemoteViews remoteViews)
    {
        AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(appWidgetId, remoteViews);
    }
}
