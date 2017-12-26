/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

public enum TimeFormat {
    HOUR_24("24 Hour"),
    HOUR_12("12 Hour");

    private String description;

    TimeFormat(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
