/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;

import com.waynedgrant.cirrus.measures.Conditions;
import com.waynedgrant.cirrus.measures.Pressure;
import com.waynedgrant.cirrus.measures.Rainfall;
import com.waynedgrant.cirrus.measures.Temperature;
import com.waynedgrant.cirrus.measures.Trend;
import com.waynedgrant.cirrus.measures.WindDirection;
import com.waynedgrant.cirrus.measures.WindSpeed;

public class ClientRaw
{
    public static final String VALID_HEADER_VALUE = "12345";
    public static final int HEADER = 0;
    public static final int AVERAGE_WIND_SPEED_KNOTS = 1;
    public static final int GUST_SPEED_KNOTS = 2;
    public static final int WIND_DIRECTION_COMPASS_DEGREES = 3;
    public static final int OUTDOOR_TEMPERATURE_CELSIUS = 4;
    public static final int OUTDOOR_HUMIDITY_PERCENTAGE = 5;
    public static final int SURFACE_PRESSURE_HECTOPASCALS = 6;
    public static final int DAILY_RAINFALL_MILLIMETRES = 7;
    public static final int RAINFALL_RATE_MILLIMETRES_PER_MINUTE = 10;
    public static final int INDOOR_TEMPERATURE_CELSIUS = 12;
    public static final int INDOOR_HUMIDITY_PERCENTAGE = 13;
    public static final int FORECAST = 15;
    public static final int HOUR = 29;
    public static final int MINUTE = 30;
    public static final int SECONDS = 31;
    public static final int STATION_NAME = 32;
    public static final int SOLAR_PERCENTAGE = 34;
    public static final int DAY = 35;
    public static final int MONTH = 36;
    public static final int WIND_CHILL_CELSIUS = 44;
    public static final int HUMIDEX_CELSIUS = 45;
    public static final int DAILY_MAX_OUTDOOR_TEMPERATURE_CELSIUS = 46;
    public static final int DAILY_MIN_OUTDOOR_TEMPERATURE_CELSIUS = 47;
    public static final int CURRENT_CONDITIONS_DESCRIPTION = 49;
    public static final int SURFACE_PRESSURE_TREND = 50;
    public static final int DEW_POINT_CELSIUS = 72;
    public static final int UV_INDEX = 79;
    public static final int HEAT_INDEX_CELSIUS = 112;
    public static final int SOLAR_RADIATION_WATTS_PER_METRE_SQUARED = 127;
    public static final int APPARENT_TEMPERATURE_CELSIUS = 130;
    public static final int YEAR = 141;
    public static final int OUTDOOR_TEMPERATURE_TREND = 143;
    public static final int OUTDOOR_HUMIDITY_TREND = 144;
    public static final int LATITUDE_DECIMAL_DEGREES = 160;
    public static final int LONGITUDE_DECIMAL_DEGREES = 161;
    
    private String[] fields;
    
    private ClientRaw(String[] fields)
    {
        this.fields = fields;
    }
    
    public static ClientRaw getInstance(InputStream is) throws IOException
    {                
        LineNumberReader lnr = null;
        String[] fields = {}; 
        
        try
        {
            lnr = new LineNumberReader(new InputStreamReader(is));
            String line = lnr.readLine();
            
            if (line != null && line.length() > 0)
            {
                fields = line.split(" ");
            }
        }
        finally
        {
            if (lnr != null)
            {
                lnr.close();
            }
        }
        
        return new ClientRaw(fields);
    }
    
    public boolean isEmpty()
    {
        return fields.length == 0;
    }

    public boolean isValid()
    {
        boolean isValid = false;
        
        String header = getHeader();
        
        if (header != null && header.equals(VALID_HEADER_VALUE))
        {
            isValid = true;
        }
         
        return isValid;
    }
    
    public String getHeader()
    {
        return getFieldValueAsString(HEADER);
    }
    
    public WindSpeed getAverageWindSpeed()
    {
        return getFieldValueAsWindSpeed(AVERAGE_WIND_SPEED_KNOTS);
    }    

    public WindSpeed getGustSpeed()
    {
        return getFieldValueAsWindSpeed(GUST_SPEED_KNOTS);
    }
    
    public WindDirection getWindDirection()
    {   
        return getFieldValueAsWindDirection(WIND_DIRECTION_COMPASS_DEGREES);
    }    
    
    public Temperature getOutdoorTemperature()
    {
        return getFieldValueAsTemperature(OUTDOOR_TEMPERATURE_CELSIUS);
    }

    public Integer getOutdoorHumidityPercentage()
    {
        return getFieldValueAsInteger(OUTDOOR_HUMIDITY_PERCENTAGE);
    }

    public Pressure getSurfacePressure()
    {
        return getFieldValueAsPressure(SURFACE_PRESSURE_HECTOPASCALS);
    }
    
    public Rainfall getDailyRainfall()
    {
        return getFieldValueAsRainfall(DAILY_RAINFALL_MILLIMETRES);
    }    

    public Rainfall getRainfallRatePerMinute()
    {
        return getFieldValueAsRainfall(RAINFALL_RATE_MILLIMETRES_PER_MINUTE);
    }
    
    public Temperature getIndoorTemperature()
    {
        return getFieldValueAsTemperature(INDOOR_TEMPERATURE_CELSIUS);
    }

    public Integer getIndoorHumidityPercentage()
    {
        return getFieldValueAsInteger(INDOOR_HUMIDITY_PERCENTAGE);
    }
    
    public Conditions getForecast()
    {
        Conditions forecast = null;
        
        Integer forecastIcon = getFieldValueAsInteger(FORECAST);
        
        if (forecastIcon != null)
        {
            forecast = Conditions.resolveIcon(forecastIcon);
        }
        
        return forecast;
    }
    
    public Integer getHour()
    {
        return getFieldValueAsInteger(HOUR);
    }

    public Integer getMinute()
    {
        return getFieldValueAsInteger(MINUTE);
    }

    public Integer getSeconds()
    {
        return getFieldValueAsInteger(SECONDS);
    }    
    
    public String getStationName()
    {
        String stationName = getFieldValueAsString(STATION_NAME);
        
        if (stationName != null)
        {
            if (stationName.equals("-"))
            {
                stationName = null;
            }
            else
            {
                stationName = removeTrailingTime(stationName);
                stationName = sanitizeString(stationName);
            }
        }
        
        return stationName;
    }
    
    public Integer getSolarPercentage()
    {
        return getFieldValueAsInteger(SOLAR_PERCENTAGE);
    }
    
    public Integer getDay()
    {
        return getFieldValueAsInteger(DAY);
    }

    public Integer getMonth()
    {
        return getFieldValueAsInteger(MONTH);
    }
    
    public Temperature getWindChill()
    {
        return getFieldValueAsTemperature(WIND_CHILL_CELSIUS);
    }
    
    public Temperature getHumidex()
    {
        return getFieldValueAsTemperature(HUMIDEX_CELSIUS);
    }   
    
    public Temperature getDailyMaxOutdoorTemperature()
    {
        return getFieldValueAsTemperature(DAILY_MAX_OUTDOOR_TEMPERATURE_CELSIUS);
    }

    public Temperature getDailyMinOutdoorTemperature()
    {
        return getFieldValueAsTemperature(DAILY_MIN_OUTDOOR_TEMPERATURE_CELSIUS);
    }    
    
    public String getCurrentConditionsDescription()
    {
        String currentConditions = getFieldValueAsString(CURRENT_CONDITIONS_DESCRIPTION);
        
        if (currentConditions != null)
        {
            currentConditions = sanitizeString(currentConditions);
        }
        
        return currentConditions;        
    }        
    
    public Trend getSurfacePressureTrend()
    {
        return getFieldValueAsTrend(SURFACE_PRESSURE_TREND);
    }    
    
    public Temperature getDewPoint()
    {
        return getFieldValueAsTemperature(DEW_POINT_CELSIUS);
    }
    
    public BigDecimal getUvIndex()
    {
        return getFieldValueAsBigDecimal(UV_INDEX);
    }
    
    public Temperature getHeatIndex()
    {
        return getFieldValueAsTemperature(HEAT_INDEX_CELSIUS);
    }
    
    public Temperature getApparentTemperature()
    {
        return getFieldValueAsTemperature(APPARENT_TEMPERATURE_CELSIUS);
    }
    
    public BigDecimal getSolarRadiation()
    {
        return getFieldValueAsBigDecimal(SOLAR_RADIATION_WATTS_PER_METRE_SQUARED);
    }
    
    public Integer getYear()
    {
        return getFieldValueAsInteger(YEAR);
    }
    
    public Trend getOutdoorTemperatureTrend()
    {    
        return getFieldValueAsTrend(OUTDOOR_TEMPERATURE_TREND);
    }
    
    public Trend getOutdoorHumidityTrend()
    {
        return getFieldValueAsTrend(OUTDOOR_HUMIDITY_TREND);
    }
    
    public BigDecimal getLatitudeDecimalDegrees()
    {
        return getFieldValueAsBigDecimal(LATITUDE_DECIMAL_DEGREES);
    }

    public BigDecimal getLongitudeDecimalDegrees()
    {
        return getFieldValueAsBigDecimal(LONGITUDE_DECIMAL_DEGREES);
    }
    
    private String sanitizeString(String value)
    {
        value = replaceUnderscoresWithSpaces(value);
        value = value.trim();
        value = minimizeSpaces(value);
        
        if (value.length() == 0)
        {
            value = null;
        }
        
        return value;
    }
    
    private String minimizeSpaces(String stationName)
    {
        stationName = stationName.replaceAll(" +", " ");
        return stationName;
    }
    
    private String replaceUnderscoresWithSpaces(String value)
    {
        return value.replaceAll("_", " ");
    }
    
    private String removeTrailingTime(String value)
    {
        // e.g. -hh:mm:ss
        int i = value.lastIndexOf("-");
        
        if (i != -1)
        {
            value = value.substring(0, i);
        }
        
        return value;
    }
    
    private WindSpeed getFieldValueAsWindSpeed(int fieldPosition)
    {
        WindSpeed value = null;
        
        BigDecimal valueKnots = getFieldValueAsBigDecimal(fieldPosition);
        
        if (valueKnots != null)
        {
            value = new WindSpeed(valueKnots);
        }
        
        return value;
    }
    
    private WindDirection getFieldValueAsWindDirection(int fieldPosition)
    {
        WindDirection value = null;
        
        Integer valueCompassDegrees = getFieldValueAsInteger(fieldPosition);
        
        if (valueCompassDegrees != null)
        {
            value = new WindDirection(valueCompassDegrees);
        }
        
        return value;
    }
    
    private Temperature getFieldValueAsTemperature(int fieldPosition)
    {
        Temperature value = null;
        
        BigDecimal valueCelsius = getFieldValueAsBigDecimal(fieldPosition);
        
        if (valueCelsius != null)
        {
            value = new Temperature(valueCelsius);
        }
        
        return value;
    }
    
    private Pressure getFieldValueAsPressure(int fieldPosition)
    {
        Pressure value = null;
        
        BigDecimal valueHectopascals = getFieldValueAsBigDecimal(fieldPosition);
        
        if (valueHectopascals != null)
        {
            value = new Pressure(valueHectopascals);
        }
        
        return value;
    }
    
    private Rainfall getFieldValueAsRainfall(int fieldPosition)
    {
        Rainfall value = null;
        
        BigDecimal valueMillimetres = getFieldValueAsBigDecimal(fieldPosition);
        
        if (valueMillimetres != null)
        {
            value = new Rainfall(valueMillimetres);
        }
        
        return value;
    }
    
    private Integer getFieldValueAsInteger(int fieldPosition)
    {
        Integer value = null;
        
        if (fields.length-1 >= fieldPosition)
        {
            try
            {
                value = Integer.parseInt(fields[fieldPosition]);
            }
            catch (NumberFormatException ex)
            {
                value = null;
            }
        }
        
        return value;
    }
    
    private BigDecimal getFieldValueAsBigDecimal(int fieldPosition)
    {
        BigDecimal value = null;
        
        if (fields.length-1 >= fieldPosition)
        {
            try
            {
                value = new BigDecimal(fields[fieldPosition]);
            }
            catch (NumberFormatException ex)
            {
                value = null;
            }
        }
        
        return value;
    }
    
    private String getFieldValueAsString(int fieldPosition)
    {
        String value = null;
        
        if (fields.length-1 >= fieldPosition)
        {
            value = fields[fieldPosition];
            
            if (value.equals("-"))
            {
                value = null;
            }            
        }
        
        return value;
    }
    
    private Trend getFieldValueAsTrend(int fieldPosition)
    {
        Trend value = null;
        
        BigDecimal bigDecimalValue = getFieldValueAsBigDecimal(fieldPosition);
        
        if (bigDecimalValue != null)
        {
            if (bigDecimalValue.compareTo(BigDecimal.valueOf(0)) > 0)
            {
                value = Trend.RISING;
            }
            else if (bigDecimalValue.compareTo(BigDecimal.valueOf(0)) < 0)
            {
                value = Trend.FALLING;
            }
            else
            {
                value = Trend.STEADY;
            }
        }
        
        return value;
    }
}
