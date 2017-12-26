/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.waynedgrant.cirrus.R.drawable.ic_launcher;
import static com.waynedgrant.cirrus.R.id.clientRawUrlEditText;
import static com.waynedgrant.cirrus.R.id.connectionTimeoutSpinner;
import static com.waynedgrant.cirrus.R.id.dateFormatSpinner;
import static com.waynedgrant.cirrus.R.id.dateFormatTextView;
import static com.waynedgrant.cirrus.R.id.displayDateCheckBox;
import static com.waynedgrant.cirrus.R.id.overrideStationNameCheckBox;
import static com.waynedgrant.cirrus.R.id.pressureUnitSpinner;
import static com.waynedgrant.cirrus.R.id.rainfallUnitSpinner;
import static com.waynedgrant.cirrus.R.id.readTimeoutSpinner;
import static com.waynedgrant.cirrus.R.id.stationNameEditText;
import static com.waynedgrant.cirrus.R.id.temperatureUnitSpinner;
import static com.waynedgrant.cirrus.R.id.timeFormatSpinner;
import static com.waynedgrant.cirrus.R.id.transparentCheckBox;
import static com.waynedgrant.cirrus.R.id.weatherItem1Spinner;
import static com.waynedgrant.cirrus.R.id.weatherItem2Spinner;
import static com.waynedgrant.cirrus.R.id.weatherItem3Spinner;
import static com.waynedgrant.cirrus.R.id.weatherItem4Spinner;
import static com.waynedgrant.cirrus.R.id.weatherItem5Spinner;
import static com.waynedgrant.cirrus.R.id.windDirectionUnitSpinner;
import static com.waynedgrant.cirrus.R.id.windSpeedUnitSpinner;
import static com.waynedgrant.cirrus.R.layout.widget_config_layout;
import static com.waynedgrant.cirrus.R.string.about_message;
import static com.waynedgrant.cirrus.R.string.about_title;
import static com.waynedgrant.cirrus.R.string.app_name;
import static com.waynedgrant.cirrus.R.string.clientRawUrlProtocolNotSupported_message;
import static com.waynedgrant.cirrus.R.string.clientRawUrlRequired_message;
import static com.waynedgrant.cirrus.R.string.stationNameRequired_message;
import static com.waynedgrant.cirrus.WidgetProvider.FETCH_FRESH_CLIENT_RAW;

public class WidgetConfigActivity extends Activity {
    private static final String TAG = "WidgetConfigActivity";

    private int appWidgetId;
    private Intent resultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);

        setContentView(widget_config_layout);

        Intent intent = getIntent();
        appWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);

        resultValue = new Intent();
        resultValue.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);

        setResult(RESULT_CANCELED, resultValue);

        if (appWidgetId == INVALID_APPWIDGET_ID) {
            finish();
        }

        populateActivity();
    }

    private void populateActivity() {
        Preferences preferences = new Preferences(getApplicationContext());

        String clientRawUrl = preferences.getClientRawUrl(appWidgetId);

        EditText editTextUrl = (EditText) findViewById(clientRawUrlEditText);
        editTextUrl.setText(clientRawUrl);

        String stationName = preferences.getStationName(appWidgetId);

        if (stationName != null) {
            CheckBox overrideStationNameCheckBoxControl = (CheckBox) findViewById(overrideStationNameCheckBox);
            overrideStationNameCheckBoxControl.setChecked(true);

            EditText stationNameEditTextControl = (EditText) findViewById(stationNameEditText);
            stationNameEditTextControl.setText(stationName);
            stationNameEditTextControl.setVisibility(VISIBLE);
        }

        WeatherItem[] weatherItems = WeatherItem.values();

        populateSpinner(weatherItem1Spinner, weatherItems, preferences.getWeatherItem1(appWidgetId));
        populateSpinner(weatherItem2Spinner, weatherItems, preferences.getWeatherItem2(appWidgetId));
        populateSpinner(weatherItem3Spinner, weatherItems, preferences.getWeatherItem3(appWidgetId));
        populateSpinner(weatherItem4Spinner, weatherItems, preferences.getWeatherItem4(appWidgetId));
        populateSpinner(weatherItem5Spinner, weatherItems, preferences.getWeatherItem5(appWidgetId));

        populateSpinner(temperatureUnitSpinner, TemperatureUnit.values(), preferences.getTemperatureUnit(appWidgetId));
        populateSpinner(pressureUnitSpinner, PressureUnit.values(), preferences.getPressureUnit(appWidgetId));
        populateSpinner(windSpeedUnitSpinner, WindSpeedUnit.values(), preferences.getWindSpeedUnit(appWidgetId));
        populateSpinner(windDirectionUnitSpinner, WindDirectionUnit.values(), preferences.getWindDirectionUnit(appWidgetId));
        populateSpinner(rainfallUnitSpinner, RainfallUnit.values(), preferences.getRainfallUnit(appWidgetId));

        populateSpinner(dateFormatSpinner, DateFormat.values(), preferences.getDateFormat(appWidgetId));

        if (preferences.getDateFormat(appWidgetId) != null) {
            CheckBox displayDateCheckBoxControl = (CheckBox) findViewById(displayDateCheckBox);
            displayDateCheckBoxControl.setChecked(true);

            TextView dateFormatTextViewControl = (TextView) findViewById(dateFormatTextView);
            dateFormatTextViewControl.setVisibility(VISIBLE);

            Spinner dateFormatSpinnerControl = (Spinner) findViewById(dateFormatSpinner);
            dateFormatSpinnerControl.setVisibility(VISIBLE);
        }

        populateSpinner(timeFormatSpinner, TimeFormat.values(), preferences.getTimeFormat(appWidgetId));

        populateSpinner(connectionTimeoutSpinner, Timeout.values(), preferences.getConnectionTimeout(appWidgetId));
        populateSpinner(readTimeoutSpinner, Timeout.values(), preferences.getReadTimeout(appWidgetId));

        boolean transparent = preferences.isTransparent(appWidgetId);

        CheckBox transparentCheckBoxControl = (CheckBox) findViewById(transparentCheckBox);
        transparentCheckBoxControl.setChecked(transparent);
    }

    private <T> void populateSpinner(int spinnerId, T[] items, T selectedItem) {
        ArrayAdapter<T> arrayAdapter = new ArrayAdapter<T>(this, simple_spinner_item, items);

        Spinner spinner = (Spinner) findViewById(spinnerId);
        arrayAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(arrayAdapter.getPosition(selectedItem));
    }

    public void onClickOverrideStationName(View view) {
        Log.d(TAG, "onClickOverrideStationName()");

        CheckBox overrideStationNameCheckBox = (CheckBox) view;
        EditText stationNameEditTextControl = (EditText) findViewById(stationNameEditText);

        if (overrideStationNameCheckBox.isChecked()) {
            stationNameEditTextControl.setVisibility(VISIBLE);
        } else {
            stationNameEditTextControl.setVisibility(GONE);
        }
    }

    public void onClickDisplayDate(View view) {
        Log.d(TAG, "onClickDisplayDate()");

        CheckBox displayDateCheckBoxControl = (CheckBox) view;
        TextView dateFormatTextViewControl = (TextView) findViewById(dateFormatTextView);
        Spinner dateFormatSpinnerControl = (Spinner) findViewById(dateFormatSpinner);

        if (displayDateCheckBoxControl.isChecked()) {
            dateFormatTextViewControl.setVisibility(VISIBLE);
            dateFormatSpinnerControl.setVisibility(VISIBLE);
        } else {
            dateFormatTextViewControl.setVisibility(GONE);
            dateFormatSpinnerControl.setVisibility(GONE);
        }
    }

    public void onClickOk(View view) {
        Log.d(TAG, "onClickOk()");

        Context context = getApplicationContext();

        if (!isClientRawUrlPopulated()) {
            displayWarningDialog(context.getString(clientRawUrlRequired_message));
        } else if (!isClientRawUrlProtocolSupported()) {
            displayWarningDialog(context.getString(clientRawUrlProtocolNotSupported_message));
        } else if (isStationNameOverridden() && !isStationNamePopulated()) {
            displayWarningDialog(context.getString(stationNameRequired_message));
        } else {
            Preferences preferences = new Preferences(getApplicationContext());
            String oldClientRawUrl = preferences.getClientRawUrl(appWidgetId);

            updatePreferences(context);

            String newClientRawUrl = preferences.getClientRawUrl(appWidgetId);

            setResult(RESULT_OK, resultValue);

            Intent updateServiceIntent = new Intent(context, UpdateWidgetService.class);
            updateServiceIntent.putExtra(EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});

            boolean fetchFreshClientRaw = !newClientRawUrl.equals(oldClientRawUrl) || !ClientRawCache.isCached(appWidgetId);
            updateServiceIntent.putExtra(FETCH_FRESH_CLIENT_RAW, fetchFreshClientRaw);

            context.startService(updateServiceIntent);

            finish();
        }
    }

    private void updatePreferences(Context context) {
        Preferences preferences = new Preferences(context);

        preferences.setConfigured(appWidgetId, true);

        preferences.setClientRawUrl(appWidgetId, ((EditText) findViewById(clientRawUrlEditText)).getText().toString().trim());

        if (isStationNameOverridden()) {
            preferences.setStationName(appWidgetId, ((EditText) findViewById(stationNameEditText)).getText().toString().trim());
        } else {
            preferences.removeStationName(appWidgetId);
        }

        preferences.setWeatherItem1(appWidgetId, (WeatherItem) ((Spinner) findViewById(weatherItem1Spinner)).getSelectedItem());
        preferences.setWeatherItem2(appWidgetId, (WeatherItem) ((Spinner) findViewById(weatherItem2Spinner)).getSelectedItem());
        preferences.setWeatherItem3(appWidgetId, (WeatherItem) ((Spinner) findViewById(weatherItem3Spinner)).getSelectedItem());
        preferences.setWeatherItem4(appWidgetId, (WeatherItem) ((Spinner) findViewById(weatherItem4Spinner)).getSelectedItem());
        preferences.setWeatherItem5(appWidgetId, (WeatherItem) ((Spinner) findViewById(weatherItem5Spinner)).getSelectedItem());

        preferences.setTemperatureUnit(appWidgetId, (TemperatureUnit) ((Spinner) findViewById(temperatureUnitSpinner)).getSelectedItem());
        preferences.setPressureUnit(appWidgetId, (PressureUnit) ((Spinner) findViewById(pressureUnitSpinner)).getSelectedItem());
        preferences.setWindSpeedUnit(appWidgetId, (WindSpeedUnit) ((Spinner) findViewById(windSpeedUnitSpinner)).getSelectedItem());
        preferences.setWindDirectionUnit(appWidgetId, (WindDirectionUnit) ((Spinner) findViewById(windDirectionUnitSpinner)).getSelectedItem());
        preferences.setRainfallUnit(appWidgetId, (RainfallUnit) ((Spinner) findViewById(rainfallUnitSpinner)).getSelectedItem());

        if (isDateToBeDisplayed()) {
            preferences.setDateFormat(appWidgetId, (DateFormat) ((Spinner) findViewById(dateFormatSpinner)).getSelectedItem());
        } else {
            preferences.removeDateFormat(appWidgetId);
        }

        preferences.setTimeFormat(appWidgetId, (TimeFormat) ((Spinner) findViewById(timeFormatSpinner)).getSelectedItem());

        preferences.setConnectionTimeout(appWidgetId, (Timeout) ((Spinner) findViewById(connectionTimeoutSpinner)).getSelectedItem());
        preferences.setReadTimeout(appWidgetId, (Timeout) ((Spinner) findViewById(readTimeoutSpinner)).getSelectedItem());

        preferences.setTransparent(appWidgetId, ((CheckBox) findViewById(transparentCheckBox)).isChecked());

        preferences.commit();
    }

    private boolean isClientRawUrlPopulated() {
        return ((EditText) findViewById(clientRawUrlEditText)).getText().toString().trim().length() > 0;
    }

    private boolean isClientRawUrlProtocolSupported() {
        // Either no protocol, http or https is supported
        boolean valid = false;

        String clientRawUrl = ((EditText) findViewById(clientRawUrlEditText)).getText().toString().trim();

        if (clientRawUrl.contains("://")) {
            if (clientRawUrl.startsWith("http://") || clientRawUrl.startsWith("https://")) {
                valid = true;
            }
        } else {
            valid = true;
        }

        return valid;
    }

    private boolean isStationNameOverridden() {
        return ((CheckBox) findViewById(overrideStationNameCheckBox)).isChecked();
    }

    private boolean isStationNamePopulated() {
        return ((EditText) findViewById(stationNameEditText)).getText().toString().trim().length() > 0;
    }

    private boolean isDateToBeDisplayed() {
        return ((CheckBox) findViewById(displayDateCheckBox)).isChecked();
    }

    private void displayWarningDialog(String warningMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle(app_name);
        alertDialog.setMessage(warningMessage);
        alertDialog.setButton(BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void onClickAbout(View view) {
        Log.d(TAG, "onClickAbout()");

        Resources resources = getResources();

        String aboutTitle =
                MessageFormat.format(resources.getString(about_title),
                        resources.getString(app_name));

        TextView message = new TextView(this);
        message.setText(Html.fromHtml(resources.getString(about_message)));
        message.setPadding(5, 5, 5, 5);
        message.setGravity(CENTER_HORIZONTAL);
        message.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle(aboutTitle);
        alertDialog.setView(message);
        alertDialog.setButton(BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setIcon(ic_launcher);

        alertDialog.show();
    }
}
