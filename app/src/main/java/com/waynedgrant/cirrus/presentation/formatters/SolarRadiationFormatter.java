/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class SolarRadiationFormatter
{
    private BigDecimal solarRadiation;

    public SolarRadiationFormatter(BigDecimal solarRadiation)
    {
        this.solarRadiation = solarRadiation;
    }

    public String format()
    {
        String formatted = "----.- W/m\u00b2";

        if (solarRadiation != null)
        {
            formatted = String.format(Locale.US, "%1.1f W/m\u00b2", solarRadiation.setScale(1, RoundingMode.HALF_DOWN));
        }

        return formatted;
    }
}
