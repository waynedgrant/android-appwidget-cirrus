/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import static com.waynedgrant.cirrus.units.PressureUnit.HECTOPASCALS;
import static com.waynedgrant.cirrus.units.PressureUnit.INCHES_OF_MERCURY;
import static com.waynedgrant.cirrus.units.PressureUnit.KILIOPASCALS;
import static com.waynedgrant.cirrus.units.PressureUnit.MILLIBARS;

import java.math.RoundingMode;
import java.util.Locale;

import com.waynedgrant.cirrus.measures.Pressure;
import com.waynedgrant.cirrus.units.PressureUnit;

public class PressureFormatter
{
    private Pressure pressure;

    public PressureFormatter(Pressure pressure)
    {
        this.pressure = pressure;
    }

    public String format(PressureUnit unit)
    {
        String formatted;

        if (pressure != null)
        {
            String formatString;
            int scale;

            if (unit == HECTOPASCALS)
            {
                formatString = "%1.1f hPa";
                scale = 1;
            }
            else if (unit == MILLIBARS)
            {
                formatString = "%1.1f mb";
                scale = 1;
            }
            else if (unit == KILIOPASCALS)
            {
                formatString = "%1.2f kPa";
                scale = 2;
            }
            else if (unit == INCHES_OF_MERCURY)
            {
                formatString = "%1.2f inHg";
                scale = 2;
            }
            else
            {
                formatString = "%1.1f mmHg";
                scale = 1;
            }

            formatted = String.format(Locale.US, formatString, pressure.getValue(unit).setScale(scale, RoundingMode.HALF_DOWN));
        }
        else
        {
            if (unit == HECTOPASCALS)
            {
                formatted = "----.- hPa";
            }
            else if (unit == MILLIBARS)
            {
                formatted = "----.- mb";
            }
            else if (unit == KILIOPASCALS)
            {
                formatted = "---.-- kPa";
            }
            else if (unit == INCHES_OF_MERCURY)
            {
                formatted = "--.-- inHg";
            }
            else
            {
                formatted = "---.- mmHg";
            }
        }

        return formatted;
    }
}
