/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import java.util.Locale;

public class HumidityFormatter
{
    private Integer humidityPercentage;
    
    public HumidityFormatter(Integer humidityPercentage)
    {
        this.humidityPercentage = humidityPercentage;
    }

    public String format()
    {
        String formatted = "--%";
        
        if (humidityPercentage != null)
        {
            formatted = String.format(Locale.US, "%1d%%", humidityPercentage);
        }
        
        return formatted;
    }
}
