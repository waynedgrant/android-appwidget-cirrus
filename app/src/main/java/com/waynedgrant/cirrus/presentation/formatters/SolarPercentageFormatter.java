/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import java.util.Locale;

public class SolarPercentageFormatter {
    private Integer solarPercentage;

    public SolarPercentageFormatter(Integer solarPercentage) {
        this.solarPercentage = solarPercentage;
    }

    public String format() {
        String formatted = "--%";

        if (solarPercentage != null) {
            formatted = String.format(Locale.US, "%1d%%", solarPercentage);
        }

        return formatted;
    }
}
