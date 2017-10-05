/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import static com.waynedgrant.cirrus.presentation.formatters.DateFormat.YYYY_MM_DD;

import java.util.Locale;

public class DateFormatter
{
    private Integer year;
    private Integer month;
    private Integer day;

    public DateFormatter(Integer year, Integer month, Integer day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String format(DateFormat dateFormat)
    {
        String formatted;

        if (year != null && month != null && day != null)
        {
            if (dateFormat == YYYY_MM_DD)
            {
                formatted = String.format(Locale.US, "%1$04d/%2$02d/%3$02d", year, month, day);
            }
            else if (dateFormat == DateFormat.DD_MM_YYYY)
            {
                formatted = String.format(Locale.US, "%1$02d/%2$02d/%3$04d", day, month, year);
            }
            else
            {
                formatted = String.format(Locale.US, "%1$02d/%2$02d/%3$04d", month, day, year);
            }
        }
        else
        {
            if (dateFormat == YYYY_MM_DD)
            {
                formatted = "----/--/--";
            }
            else
            {
                formatted = "--/--/----";
            }
        }

        return formatted;
    }
}
