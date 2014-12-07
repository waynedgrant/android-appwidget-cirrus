/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.units;

public enum PressureUnit
{
    HECTOPASCALS("Hectopascals (hPa)"),
    INCHES_OF_MERCURY("Inches of Mercury (inHg)"),
    KILIOPASCALS("Kilopascals (kPa)"),
    MILLIBARS("Millibars (mb)"),
    MILLIMETRES_OF_MERCURY("Millimetres of Mercury (mmHg)");
    
    private String description;
    
    PressureUnit(String description)
    {
        this.description = description;
    }
    
    public String toString()
    {
        return description;
    }
}
