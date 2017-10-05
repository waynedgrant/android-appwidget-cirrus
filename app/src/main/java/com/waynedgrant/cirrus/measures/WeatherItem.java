/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

public enum WeatherItem
{
    APPARENT_TEMPERATURE("Apparent Temperature"),
    AVERAGE_WIND("Average Wind"),
    BLANK("Blank"),
    DAILY_RAINFALL("Daily Rainfall"),
    DEW_POINT("Dew Point"),
    FORECAST("Forecast"),
    GUST("Gust"),
    HEAT_INDEX("Heat Index"),
    HUMIDEX("Humidex"),
    HUMIDITY("Humidity"),
    INDOOR_CONDITIONS("Indoor Conditions"),
    RAINFALL_RATE("Rainfall Rate"),
    SOLAR("Solar"),
    SURFACE_PRESSURE("Surface Pressure"),
    UV_INDEX("UV Index"),
    WIND_CHILL("Wind Chill");

    private String description;

    WeatherItem(String description)
    {
        this.description = description;
    }

    public String toString()
    {
        return description;
    }
}
