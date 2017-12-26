/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Trend;

public class TrendFormatter {
    private Trend trend;

    public TrendFormatter(Trend trend) {
        this.trend = trend;
    }

    public String format() {
        String formatted = "-";

        if (trend == Trend.RISING) {
            formatted = "\u279A";
        } else if (trend == Trend.STEADY) {
            formatted = "\u279E";
        } else if (trend == Trend.FALLING) {
            formatted = "\u2798";
        }

        return formatted;
    }
}
