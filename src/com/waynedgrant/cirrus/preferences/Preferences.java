/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.waynedgrant.cirrus.measures.WeatherItem;
import com.waynedgrant.cirrus.presentation.formatters.DateFormat;
import com.waynedgrant.cirrus.presentation.formatters.TimeFormat;
import com.waynedgrant.cirrus.units.PressureUnit;
import com.waynedgrant.cirrus.units.RainfallUnit;
import com.waynedgrant.cirrus.units.TemperatureUnit;
import com.waynedgrant.cirrus.units.WindDirectionUnit;
import com.waynedgrant.cirrus.units.WindSpeedUnit;
import com.waynedgrant.cirrus.update.Timeout;

public class Preferences
{
    private SharedPreferences preferences;
    private Editor editor;
    
    private static final String CONFIGURED = "configured";
    private static final String CLIENT_RAW_URL = "client-raw-url";
    private static final String STATION_NAME = "station_name";
    private static final String WEATHER_ITEM_1 = "weather-item-1";
    private static final String WEATHER_ITEM_2 = "weather-item-2";
    private static final String WEATHER_ITEM_3 = "weather-item-3";
    private static final String WEATHER_ITEM_4 = "weather-item-4";
    private static final String WEATHER_ITEM_5 = "weather-item-5";
    private static final String TEMPERATURE_UNIT = "temperature-unit";
    private static final String PRESSURE_UNIT = "pressure-unit";
    private static final String WIND_SPEED_UNIT = "wind-speed-unit";
    private static final String WIND_DIRECTION_UNIT = "wind-direction-unit";
    private static final String RAINFALL_UNIT = "rainfall-unit";
    private static final String DATE_FORMAT = "date-format";
    private static final String TIME_FORMAT = "time-format";
    private static final String TRANSPARENT = "transparent";
    private static final String CONNECTION_TIMEOUT = "connection-timeout";
    private static final String READ_TIMEOUT = "read-timeout";
    
    public Preferences(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    public boolean isConfigured(int appWidgetId)
    {
        return Boolean.valueOf(getPreference(CONFIGURED, appWidgetId, Boolean.FALSE.toString()));
    }
    
    public void setConfigured(int appWidgetId, boolean value)
    {
        setPreference(CONFIGURED, appWidgetId, Boolean.toString(value));
    }
    
    public String getClientRawUrl(int appWidgetId)
    {
        return getPreference(CLIENT_RAW_URL, appWidgetId, null);
    }
    
    public void setClientRawUrl(int appWidgetId, String value)
    {
        setPreference(CLIENT_RAW_URL, appWidgetId, value);
    }
    
    public String getStationName(int appWidgetId)
    {
        return getPreference(STATION_NAME, appWidgetId, null);
    }
    
    public void setStationName(int appWidgetId, String value)
    {
        setPreference(STATION_NAME, appWidgetId, value);
    }
    
    public void removeStationName(int appWidgetId)
    {
        removePreference(STATION_NAME, appWidgetId);
    }
    
    public WeatherItem getWeatherItem1(int appWidgetId)
    {
        return WeatherItem.valueOf(getPreference(WEATHER_ITEM_1, appWidgetId, WeatherItem.SURFACE_PRESSURE.name()));
    }
    
    public void setWeatherItem1(int appWidgetId, WeatherItem value)
    {
        setPreference(WEATHER_ITEM_1, appWidgetId, value.name());
    }
    
    public WeatherItem getWeatherItem2(int appWidgetId)
    {
        return WeatherItem.valueOf(getPreference(WEATHER_ITEM_2, appWidgetId, WeatherItem.HUMIDITY.name()));
    }
    
    public void setWeatherItem2(int appWidgetId, WeatherItem value)
    {
        setPreference(WEATHER_ITEM_2, appWidgetId, value.name());
    }
    
    public WeatherItem getWeatherItem3(int appWidgetId)
    {
        return WeatherItem.valueOf(getPreference(WEATHER_ITEM_3, appWidgetId, WeatherItem.DEW_POINT.name()));
    }
    
    public void setWeatherItem3(int appWidgetId, WeatherItem value)
    {
        setPreference(WEATHER_ITEM_3, appWidgetId, value.name());
    }
    
    public WeatherItem getWeatherItem4(int appWidgetId)
    {
        return WeatherItem.valueOf(getPreference(WEATHER_ITEM_4, appWidgetId, WeatherItem.AVERAGE_WIND.name()));
    }
    
    public void setWeatherItem4(int appWidgetId, WeatherItem value)
    {
        setPreference(WEATHER_ITEM_4, appWidgetId, value.name());
    }
    
    public WeatherItem getWeatherItem5(int appWidgetId)
    {
        return WeatherItem.valueOf(getPreference(WEATHER_ITEM_5, appWidgetId, WeatherItem.DAILY_RAINFALL.name()));
    }
    
    public void setWeatherItem5(int appWidgetId, WeatherItem value)
    {
        setPreference(WEATHER_ITEM_5, appWidgetId, value.name());
    }
    
    public TemperatureUnit getTemperatureUnit(int appWidgetId)
    {
        return TemperatureUnit.valueOf(getPreference(TEMPERATURE_UNIT, appWidgetId, TemperatureUnit.CELSIUS.name()));
    }
    
    public void setTemperatureUnit(int appWidgetId, TemperatureUnit value)
    {
        setPreference(TEMPERATURE_UNIT, appWidgetId, value.name());
    }
    
    public PressureUnit getPressureUnit(int appWidgetId)
    {
        return PressureUnit.valueOf(getPreference(PRESSURE_UNIT, appWidgetId, PressureUnit.HECTOPASCALS.name()));
    }
    
    public void setPressureUnit(int appWidgetId, PressureUnit value)
    {
        setPreference(PRESSURE_UNIT, appWidgetId, value.name());
    }
    
    public WindSpeedUnit getWindSpeedUnit(int appWidgetId)
    {
        return WindSpeedUnit.valueOf(getPreference(WIND_SPEED_UNIT, appWidgetId, WindSpeedUnit.KILOMETRES_PER_HOUR.name()));
    }
    
    public void setWindSpeedUnit(int appWidgetId, WindSpeedUnit value)
    {
        setPreference(WIND_SPEED_UNIT, appWidgetId, value.name());
    }
    
    public WindDirectionUnit getWindDirectionUnit(int appWidgetId)
    {
        return WindDirectionUnit.valueOf(getPreference(WIND_DIRECTION_UNIT, appWidgetId, WindDirectionUnit.CARDINAL_DIRECTION.name()));
    }
    
    public void setWindDirectionUnit(int appWidgetId, WindDirectionUnit value)
    {
        setPreference(WIND_DIRECTION_UNIT, appWidgetId, value.name());
    }
    
    public RainfallUnit getRainfallUnit(int appWidgetId)
    {
        return RainfallUnit.valueOf(getPreference(RAINFALL_UNIT, appWidgetId, RainfallUnit.MILLIMETRES.name()));
    }
    
    public void setRainfallUnit(int appWidgetId, RainfallUnit value)
    {
        setPreference(RAINFALL_UNIT, appWidgetId, value.name());
    }
    
    public DateFormat getDateFormat(int appWidgetId)
    {
        String dateFormatStr = getPreference(DATE_FORMAT, appWidgetId, null);
        DateFormat dateFormat = null;
        
        if (dateFormatStr != null)
        {            
            dateFormat = DateFormat.valueOf(dateFormatStr);
        }
        
        return dateFormat;
    }
    
    public void setDateFormat(int appWidgetId, DateFormat value)
    {
        setPreference(DATE_FORMAT, appWidgetId, value.name());
    }
    
    public void removeDateFormat(int appWidgetId)
    {
        removePreference(DATE_FORMAT, appWidgetId);
    }
    
    public TimeFormat getTimeFormat(int appWidgetId)
    {
        return TimeFormat.valueOf(getPreference(TIME_FORMAT, appWidgetId, TimeFormat.HOUR_24.name()));
    }
    
    public void setTimeFormat(int appWidgetId, TimeFormat value)
    {
        setPreference(TIME_FORMAT, appWidgetId, value.name());
    }
    
    public boolean isTransparent(int appWidgetId)
    {
        return Boolean.valueOf(getPreference(TRANSPARENT, appWidgetId, Boolean.FALSE.toString()));
    }
    
    public void setTransparent(int appWidgetId, boolean value)
    {
        setPreference(TRANSPARENT, appWidgetId, Boolean.toString(value));
    }
    
    public Timeout getConnectionTimeout(int appWidgetId)
    {
        return Timeout.valueOf(getPreference(CONNECTION_TIMEOUT, appWidgetId, Timeout.TEN_SECONDS.name()));
    }
    
    public void setConnectionTimeout(int appWidgetId, Timeout value)
    {
        setPreference(CONNECTION_TIMEOUT, appWidgetId, value.name());
    }
    
    public Timeout getReadTimeout(int appWidgetId)
    {
        return Timeout.valueOf(getPreference(READ_TIMEOUT, appWidgetId, Timeout.TEN_SECONDS.name()));
    }
    
    public void setReadTimeout(int appWidgetId, Timeout value)
    {
        setPreference(READ_TIMEOUT, appWidgetId, value.name());
    }
    
    public void removePreferences(int appWidgetId)
    {
        removePreference(CONFIGURED, appWidgetId);
        removePreference(CLIENT_RAW_URL, appWidgetId);
        removePreference(STATION_NAME, appWidgetId);
        removePreference(WEATHER_ITEM_1, appWidgetId);
        removePreference(WEATHER_ITEM_2, appWidgetId);
        removePreference(WEATHER_ITEM_3, appWidgetId);
        removePreference(WEATHER_ITEM_4, appWidgetId);
        removePreference(WEATHER_ITEM_5, appWidgetId);
        removePreference(TEMPERATURE_UNIT, appWidgetId);
        removePreference(PRESSURE_UNIT, appWidgetId);
        removePreference(WIND_SPEED_UNIT, appWidgetId);
        removePreference(WIND_DIRECTION_UNIT, appWidgetId);
        removePreference(RAINFALL_UNIT, appWidgetId);
        removePreference(DATE_FORMAT, appWidgetId);
        removePreference(TIME_FORMAT, appWidgetId);
        removePreference(TRANSPARENT, appWidgetId);
        removePreference(CONNECTION_TIMEOUT, appWidgetId);
        removePreference(READ_TIMEOUT, appWidgetId);
    }
    
    public void commit()
    {
        if (editor != null)
        {
            editor.commit();
            editor = null;
        }
    }
    
    private String getPreference(String key, int appWidgetId, String defaultValue)
    {
        return preferences.getString(getKeyForAppWidgetId(key, appWidgetId), defaultValue);
    }
    
    @SuppressLint("CommitPrefEdits")
    private void setPreference(String key, int appWidgetId, String value)
    {
        if (editor == null)
        {
            editor = preferences.edit();
        }
        
        editor.putString(getKeyForAppWidgetId(key, appWidgetId), value);
    }
    
    @SuppressLint("CommitPrefEdits")
    private void removePreference(String key, int appWidgetId)
    {
        if (editor == null)
        {
            editor = preferences.edit();
        }
        
        editor.remove(getKeyForAppWidgetId(key, appWidgetId));
    }
    
    private String getKeyForAppWidgetId(String key, int appWidgetId)
    {
        return key += "-" + appWidgetId;
    }
}
