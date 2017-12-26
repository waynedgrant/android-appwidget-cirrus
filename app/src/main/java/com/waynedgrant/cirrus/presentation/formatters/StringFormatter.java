/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

public class StringFormatter {
    private String string;

    public StringFormatter(String string) {
        this.string = string;
    }

    public String format() {
        String formatted = "-----";

        if (string != null) {
            formatted = string;
        }

        return formatted;
    }
}
