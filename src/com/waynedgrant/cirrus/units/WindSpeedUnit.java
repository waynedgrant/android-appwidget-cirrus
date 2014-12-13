/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.units;

public enum WindSpeedUnit
{
    BEAUFORT_SCALE("Beaufort Scale (Bft)"),
    KILOMETRES_PER_HOUR("Kilometres per Hour (km/h)"),
    KNOTS("Knots (kts)"),
    METRES_PER_SECOND("Metres per Second (m/s)"),
    MILES_PER_HOUR("Miles per Hour (mph)");
    
    private String description;
    
    WindSpeedUnit(String description)
    {
        this.description = description;
    }
    
    public String toString()
    {
        return description;
    }
}
