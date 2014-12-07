/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.units;

public enum RainfallUnit
{
    INCHES("Inches (in)"),
    MILLIMETRES("Millimetres (mm)");
    
    private String description;
    
    RainfallUnit(String description)
    {
        this.description = description;
    }
    
    public String toString()
    {
        return description;
    }
}
