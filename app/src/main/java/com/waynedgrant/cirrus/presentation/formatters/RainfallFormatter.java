/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import static com.waynedgrant.cirrus.units.RainfallUnit.MILLIMETRES;

import java.math.RoundingMode;
import java.util.Locale;

import com.waynedgrant.cirrus.measures.Rainfall;
import com.waynedgrant.cirrus.units.RainfallUnit;

public class RainfallFormatter
{
    private Rainfall rainfall;

    public RainfallFormatter(Rainfall rainfall)
    {
        this.rainfall = rainfall;
    }

    public String format(RainfallUnit unit)
    {
        String formatted;

        if (rainfall != null)
        {
            String formatString;

            if (unit == MILLIMETRES)
            {
                formatString = "%1.2f mm";
            }
            else
            {
                formatString = "%1.2f in";
            }

            formatted = String.format(Locale.US, formatString, rainfall.getValue(unit).setScale(2, RoundingMode.HALF_DOWN));
        }
        else
        {
            if (unit == MILLIMETRES)
            {
                formatted = "-.-- mm";
            }
            else
            {
                formatted = "-.-- in";
            }
        }

        return formatted;
    }
}
