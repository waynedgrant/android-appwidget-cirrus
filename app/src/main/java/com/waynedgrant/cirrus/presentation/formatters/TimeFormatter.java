/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import static com.waynedgrant.cirrus.presentation.formatters.TimeFormat.HOUR_24;

import java.util.Locale;

public class TimeFormatter
{
    private Integer hour;
    private Integer minute;

    public TimeFormatter(Integer hour, Integer minute)
    {
        this.hour = hour;
        this.minute = minute;
    }

    public String format(TimeFormat timeFormat)
    {
        String formatted;

        if (hour != null && minute != null)
        {
            if (timeFormat == HOUR_24)
            {
                formatted = String.format(Locale.US, "%1$02d:%2$02d", hour, minute);
            }
            else
            {
                formatted = String.format(Locale.US, "%1$01d:%2$02d %3$2s", getTwelveHour(hour), minute, getTwelveHourPeriod(hour));
            }
        }
        else
        {
            if (timeFormat == HOUR_24)
            {
                formatted = "--:--";
            }
            else
            {
                formatted = "--:-- --";
            }
        }

        return formatted;
    }

    private int getTwelveHour(int hour24)
    {
        int twelveHour = hour24;

        if (twelveHour == 0)
        {
            twelveHour = 12;
        }
        else if (twelveHour >= 12)
        {
            if (twelveHour > 12)
            {
                twelveHour -= 12;
            }
        }

        return twelveHour;
    }

    private String getTwelveHourPeriod(int hour24)
    {
        String period;

        if (hour24 >= 12)
        {
            period = "PM";
        }
        else
        {
            period = "AM";
        }

        return period;
    }
}
