/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Rainfall;
import com.waynedgrant.cirrus.units.RainfallUnit;

import java.math.RoundingMode;
import java.util.Locale;

import static com.waynedgrant.cirrus.units.RainfallUnit.MILLIMETRES;

public class RainfallRateFormatter {
    private Rainfall rainfallRatePerMinute;

    public RainfallRateFormatter(Rainfall rainfallRatePerMinute) {
        this.rainfallRatePerMinute = rainfallRatePerMinute;
    }

    public String format(RainfallUnit unit) {
        String formatted;

        if (rainfallRatePerMinute != null) {
            String formatString;
            int scale;

            if (unit == MILLIMETRES) {
                formatString = "%1.2f mm/min";
                scale = 2;
            } else {
                formatString = "%1.3f in/min";
                scale = 3;
            }

            formatted = String.format(Locale.US, formatString, rainfallRatePerMinute.getValue(unit).setScale(scale, RoundingMode.HALF_DOWN));
        } else {
            if (unit == MILLIMETRES) {
                formatted = "-.-- mm/min";
            } else {
                formatted = "-.--- in/min";
            }
        }

        return formatted;
    }
}

