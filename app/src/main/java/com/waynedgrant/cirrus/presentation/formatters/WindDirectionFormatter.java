/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import static com.waynedgrant.cirrus.units.WindDirectionUnit.COMPASS_DEGREES;

import java.util.Locale;

import com.waynedgrant.cirrus.measures.WindDirection;
import com.waynedgrant.cirrus.units.WindDirectionUnit;

public class WindDirectionFormatter
{
    private WindDirection windDirection;

    public WindDirectionFormatter(WindDirection windDirection)
    {
        this.windDirection = windDirection;
    }

    public String format(WindDirectionUnit unit)
    {
        String formatted;

        if (windDirection != null)
        {
            if (unit == COMPASS_DEGREES)
            {
                formatted = String.format(Locale.US, "%1d°", windDirection.getCompassDegrees());
            }
            else
            {
                formatted = String.format(Locale.US, "%1s", windDirection.getCardinalDirection().name());
            }
        }
        else
        {
            if (unit == WindDirectionUnit.COMPASS_DEGREES)
            {
                formatted = "---°";
            }
            else
            {
                formatted = "---";
            }
        }

        return formatted;
    }
}
