/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.waynedgrant.cirrus.clientraw.ClientRawCache;
import com.waynedgrant.cirrus.measures.WeatherItem;
import com.waynedgrant.cirrus.preferences.Preferences;
import com.waynedgrant.cirrus.presentation.formatters.DateFormat;
import com.waynedgrant.cirrus.presentation.formatters.TimeFormat;
import com.waynedgrant.cirrus.units.PressureUnit;
import com.waynedgrant.cirrus.units.RainfallUnit;
import com.waynedgrant.cirrus.units.TemperatureUnit;
import com.waynedgrant.cirrus.units.WindDirectionUnit;
import com.waynedgrant.cirrus.units.WindSpeedUnit;
import com.waynedgrant.cirrus.update.Timeout;

import java.text.MessageFormat;

public class WidgetConfigActivity extends Activity
{
    private int appWidgetId;
    private Intent resultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.widget_config_layout);

        Intent intent = getIntent();
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        setResult(RESULT_CANCELED, resultValue);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            finish();
        }

        populateActivity();
    }

    private void populateActivity()
    {
        Preferences preferences = new Preferences(getApplicationContext());

        String clientRawUrl = preferences.getClientRawUrl(appWidgetId);

        EditText editTextUrl = (EditText)findViewById(R.id.clientRawUrlEditText);
        editTextUrl.setText(clientRawUrl);

        String stationName = preferences.getStationName(appWidgetId);

        if (stationName != null)
        {
            CheckBox overrideStationNameCheckBox = (CheckBox)findViewById(R.id.overrideStationNameCheckBox);
            overrideStationNameCheckBox.setChecked(true);

            EditText stationNameEditText = (EditText)findViewById(R.id.stationNameEditText);
            stationNameEditText.setText(stationName);
            stationNameEditText.setVisibility(View.VISIBLE);
        }

        WeatherItem[] weatherItems = WeatherItem.values();

        populateSpinner(R.id.weatherItem1Spinner, weatherItems, preferences.getWeatherItem1(appWidgetId));
        populateSpinner(R.id.weatherItem2Spinner, weatherItems, preferences.getWeatherItem2(appWidgetId));
        populateSpinner(R.id.weatherItem3Spinner, weatherItems, preferences.getWeatherItem3(appWidgetId));
        populateSpinner(R.id.weatherItem4Spinner, weatherItems, preferences.getWeatherItem4(appWidgetId));
        populateSpinner(R.id.weatherItem5Spinner, weatherItems, preferences.getWeatherItem5(appWidgetId));

        populateSpinner(R.id.temperatureUnitSpinner, TemperatureUnit.values(), preferences.getTemperatureUnit(appWidgetId));
        populateSpinner(R.id.pressureUnitSpinner, PressureUnit.values(), preferences.getPressureUnit(appWidgetId));
        populateSpinner(R.id.windSpeedUnitSpinner, WindSpeedUnit.values(), preferences.getWindSpeedUnit(appWidgetId));
        populateSpinner(R.id.windDirectionUnitSpinner, WindDirectionUnit.values(), preferences.getWindDirectionUnit(appWidgetId));
        populateSpinner(R.id.rainfallUnitSpinner, RainfallUnit.values(), preferences.getRainfallUnit(appWidgetId));

        populateSpinner(R.id.dateFormatSpinner, DateFormat.values(), preferences.getDateFormat(appWidgetId));

        if (preferences.getDateFormat(appWidgetId) != null)
        {
            CheckBox displayDateCheckBox = (CheckBox)findViewById(R.id.displayDateCheckBox);
            displayDateCheckBox.setChecked(true);

            TextView dateFormatTextView = (TextView)findViewById(R.id.dateFormatTextView);
            dateFormatTextView.setVisibility(View.VISIBLE);

            Spinner dateFormatSpinner = (Spinner)findViewById(R.id.dateFormatSpinner);
            dateFormatSpinner.setVisibility(View.VISIBLE);
        }

        populateSpinner(R.id.timeFormatSpinner, TimeFormat.values(), preferences.getTimeFormat(appWidgetId));

        populateSpinner(R.id.connectionTimeoutSpinner, Timeout.values(), preferences.getConnectionTimeout(appWidgetId));
        populateSpinner(R.id.readTimeoutSpinner, Timeout.values(), preferences.getReadTimeout(appWidgetId));

        boolean transparent = preferences.isTransparent(appWidgetId);

        CheckBox transparentCheckBox = (CheckBox)findViewById(R.id.transparentCheckBox);
        transparentCheckBox.setChecked(transparent);
    }

    private <T> void populateSpinner(int spinnerId, T[] items, T selectedItem)
    {
        ArrayAdapter<T> arrayAdapter = new ArrayAdapter<T>(this, android.R.layout.simple_spinner_item, items);

        Spinner spinner = (Spinner)findViewById(spinnerId);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(arrayAdapter.getPosition(selectedItem));
    }

    public void onClickOverrideStationName(View view)
    {
        CheckBox overrideStationNameCheckBox = (CheckBox)view;
        EditText stationNameEditText = (EditText)findViewById(R.id.stationNameEditText);

        if (overrideStationNameCheckBox.isChecked())
        {
            stationNameEditText.setVisibility(View.VISIBLE);
        }
        else
        {
            stationNameEditText.setVisibility(View.GONE);
        }
    }

    public void onClickDisplayDate(View view)
    {
        CheckBox displayDateCheckBox = (CheckBox)view;
        TextView dateFormatTextView = (TextView)findViewById(R.id.dateFormatTextView);
        Spinner dateFormatSpinner = (Spinner)findViewById(R.id.dateFormatSpinner);

        if (displayDateCheckBox.isChecked())
        {
            dateFormatTextView.setVisibility(View.VISIBLE);
            dateFormatSpinner.setVisibility(View.VISIBLE);
        }
        else
        {
            dateFormatTextView.setVisibility(View.GONE);
            dateFormatSpinner.setVisibility(View.GONE);
        }
    }

    public void onClickOk(View view)
    {
        Context context = getApplicationContext();

        if (!isClientRawUrlPopulated())
        {
            displayWarningDialog(context.getString(R.string.clientRawUrlRequired_message));
        }
        else if (!isClientRawUrlProtocolSupported())
        {
            displayWarningDialog(context.getString(R.string.clientRawUrlProtocolNotSupported_message));
        }
        else if (isStationNameOverridden() && !isStationNamePopulated())
        {
            displayWarningDialog(context.getString(R.string.stationNameRequired_message));
        }
        else
        {
            Preferences preferences = new Preferences(getApplicationContext());
            String oldClientRawUrl = preferences.getClientRawUrl(appWidgetId);

            updatePreferences(context);

            String newClientRawUrl = preferences.getClientRawUrl(appWidgetId);

            setResult(RESULT_OK, resultValue);

            Intent updateServiceIntent = new Intent(context, UpdateWidgetService.class);
            updateServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});

            boolean fetchFreshClientRaw = !newClientRawUrl.equals(oldClientRawUrl) || !ClientRawCache.isCached(appWidgetId);
            updateServiceIntent.putExtra(WidgetProvider.FETCH_FRESH_CLIENT_RAW, fetchFreshClientRaw);

            context.startService(updateServiceIntent);

            finish();
        }
    }

    private void updatePreferences(Context context)
    {
        Preferences preferences = new Preferences(context);

        preferences.setConfigured(appWidgetId, true);

        preferences.setClientRawUrl(appWidgetId, ((EditText)findViewById(R.id.clientRawUrlEditText)).getText().toString().trim());

        if (isStationNameOverridden())
        {
            preferences.setStationName(appWidgetId, ((EditText)findViewById(R.id.stationNameEditText)).getText().toString().trim());
        }
        else
        {
            preferences.removeStationName(appWidgetId);
        }

        preferences.setWeatherItem1(appWidgetId, (WeatherItem)((Spinner)findViewById(R.id.weatherItem1Spinner)).getSelectedItem());
        preferences.setWeatherItem2(appWidgetId, (WeatherItem)((Spinner)findViewById(R.id.weatherItem2Spinner)).getSelectedItem());
        preferences.setWeatherItem3(appWidgetId, (WeatherItem)((Spinner)findViewById(R.id.weatherItem3Spinner)).getSelectedItem());
        preferences.setWeatherItem4(appWidgetId, (WeatherItem)((Spinner)findViewById(R.id.weatherItem4Spinner)).getSelectedItem());
        preferences.setWeatherItem5(appWidgetId, (WeatherItem)((Spinner)findViewById(R.id.weatherItem5Spinner)).getSelectedItem());

        preferences.setTemperatureUnit(appWidgetId, (TemperatureUnit)((Spinner)findViewById(R.id.temperatureUnitSpinner)).getSelectedItem());
        preferences.setPressureUnit(appWidgetId, (PressureUnit)((Spinner)findViewById(R.id.pressureUnitSpinner)).getSelectedItem());
        preferences.setWindSpeedUnit(appWidgetId, (WindSpeedUnit)((Spinner)findViewById(R.id.windSpeedUnitSpinner)).getSelectedItem());
        preferences.setWindDirectionUnit(appWidgetId, (WindDirectionUnit)((Spinner)findViewById(R.id.windDirectionUnitSpinner)).getSelectedItem());
        preferences.setRainfallUnit(appWidgetId, (RainfallUnit)((Spinner)findViewById(R.id.rainfallUnitSpinner)).getSelectedItem());

        if (isDateToBeDisplayed())
        {
            preferences.setDateFormat(appWidgetId, (DateFormat)((Spinner)findViewById(R.id.dateFormatSpinner)).getSelectedItem());
        }
        else
        {
            preferences.removeDateFormat(appWidgetId);
        }

        preferences.setTimeFormat(appWidgetId, (TimeFormat)((Spinner)findViewById(R.id.timeFormatSpinner)).getSelectedItem());

        preferences.setConnectionTimeout(appWidgetId, (Timeout)((Spinner)findViewById(R.id.connectionTimeoutSpinner)).getSelectedItem());
        preferences.setReadTimeout(appWidgetId, (Timeout)((Spinner)findViewById(R.id.readTimeoutSpinner)).getSelectedItem());

        preferences.setTransparent(appWidgetId, ((CheckBox)findViewById(R.id.transparentCheckBox)).isChecked());

        preferences.commit();
    }

    private boolean isClientRawUrlPopulated()
    {
        return ((EditText)findViewById(R.id.clientRawUrlEditText)).getText().toString().trim().length() > 0;
    }

    private boolean isClientRawUrlProtocolSupported()
    {
        // Either no protocol, http or https is supported
        boolean valid = false;

        String clientRawUrl = ((EditText)findViewById(R.id.clientRawUrlEditText)).getText().toString().trim();

        if (clientRawUrl.contains("://"))
        {
            if (clientRawUrl.startsWith("http://") || clientRawUrl.startsWith("https://"))
            {
                valid = true;
            }
        }
        else
        {
            valid = true;
        }

        return valid;
    }

    private boolean isStationNameOverridden()
    {
        return ((CheckBox)findViewById(R.id.overrideStationNameCheckBox)).isChecked();
    }

    private boolean isStationNamePopulated()
    {
        return ((EditText)findViewById(R.id.stationNameEditText)).getText().toString().trim().length() > 0;
    }

    private boolean isDateToBeDisplayed()
    {
        return ((CheckBox)findViewById(R.id.displayDateCheckBox)).isChecked();
    }

    private void displayWarningDialog(String warningMessage)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(warningMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void onClickAbout(View view)
    {
        Resources resources = getResources();

        String aboutTitle =
                MessageFormat.format(resources.getString(R.string.about_title),
                        resources.getString(R.string.app_name));

        TextView message = new TextView(this);
        message.setText(Html.fromHtml(resources.getString(R.string.about_message)));
        message.setPadding(5, 5, 5, 5);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        message.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle(aboutTitle);
        alertDialog.setView(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setIcon(R.drawable.ic_launcher);

        alertDialog.show();
    }
}
