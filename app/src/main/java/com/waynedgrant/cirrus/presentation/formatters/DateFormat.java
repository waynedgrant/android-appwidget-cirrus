/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

public enum DateFormat
{
    YYYY_MM_DD("YYYY/MM/DD"),
    DD_MM_YYYY("DD/MM/YYYY"),
    MM_DD_YYYY("MM/DD/YYYY");

    private String description;

    DateFormat(String description)
    {
        this.description = description;
    }

    public String toString()
    {
        return description;
    }
}
