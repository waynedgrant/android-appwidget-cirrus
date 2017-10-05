/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.colorizers;

import com.waynedgrant.cirrus.measures.Temperature;
import com.waynedgrant.cirrus.units.TemperatureUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TemperatureColorizer
{
    public static final int WHITE = 0xffffffff;
    public static final int LIGHT_BLUE = 0xff69c3ff;
    public static final int DARK_GREEN = 0xffaaffaa;
    public static final int MEDIUM_GREEN = 0xffc3ff5d;
    public static final int LIGHT_GREEN = 0xffdefc4e;
    public static final int LIGHT_YELLOW = 0xfffff83b;
    public static final int MEDIUM_YELLOW = 0xffffdc36;
    public static final int DARK_YELLOW = 0xffffcd30;
    public static final int LIGHT_ORANGE = 0xffffb230;
    public static final int MEDIUM_ORANGE = 0xffff9b25;
    public static final int DARK_ORANGE = 0xffff8700;

    private Temperature temperature;

    public TemperatureColorizer(Temperature temperature)
    {
        this.temperature = temperature;
    }

    public int colorize()
    {
        int colorCode = WHITE;

        if (temperature != null)
        {
            BigDecimal temperatureCelsius = temperature.getValue(TemperatureUnit.CELSIUS);
            int temperatureCelsiusRounded = temperatureCelsius.setScale(0, RoundingMode.HALF_DOWN).intValue();

            if (temperatureCelsiusRounded < 1)
            {
                colorCode = LIGHT_BLUE;
            }
            else if (temperatureCelsiusRounded > 24)
            {
                colorCode = DARK_ORANGE;
            }
            else
            {
                switch (temperatureCelsiusRounded)
                {
                    case 1:
                    case 2:
                    case 3: colorCode = DARK_GREEN; break;

                    case 4:
                    case 5:
                    case 6: colorCode = MEDIUM_GREEN; break;

                    case 7:
                    case 8:
                    case 9: colorCode = LIGHT_GREEN; break;

                    case 10:
                    case 11:
                    case 12: colorCode = LIGHT_YELLOW; break;

                    case 13:
                    case 14:
                    case 15: colorCode = MEDIUM_YELLOW; break;

                    case 16:
                    case 17:
                    case 18: colorCode = DARK_YELLOW; break;

                    case 19:
                    case 20:
                    case 21: colorCode = LIGHT_ORANGE; break;

                    case 22:
                    case 23:
                    case 24: colorCode = MEDIUM_ORANGE; break;
                }
            }
        }

        return colorCode;
    }
}
