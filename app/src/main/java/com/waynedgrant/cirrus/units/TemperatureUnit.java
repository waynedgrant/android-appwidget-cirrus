/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.units;

public enum TemperatureUnit
{
    CELSIUS("Celsius (°C)"),
    FAHRENHEIT("Fahrenheit (°F)");

    private String description;

    TemperatureUnit(String description)
    {
        this.description = description;
    }

    public String toString()
    {
        return description;
    }
}
