/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.colorizers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UvIndexColorizer {
    public static final int WHITE = 0xffffffff;
    public static final int GREEN = 0xff289500;
    public static final int YELLOW = 0xfff7e400;
    public static final int ORANGE = 0xfff85900;
    public static final int RED = 0xffd8001d;
    public static final int PURPLE = 0xff6b49c8;

    private BigDecimal uvIndex;

    public UvIndexColorizer(BigDecimal uvIndex) {
        this.uvIndex = uvIndex;
    }

    public int colorize() {
        // 0-2 green, 3-5 yellow, 6-7 orange, 8-10 red, 11+ purple

        int colorCode = WHITE;

        if (uvIndex != null) {
            int uvIndexRounded = uvIndex.setScale(0, RoundingMode.HALF_DOWN).intValue();

            if (uvIndexRounded < 3) {
                colorCode = GREEN;
            } else if (uvIndexRounded > 10) {
                colorCode = PURPLE;
            } else {
                switch (uvIndexRounded) {
                    case 3:
                    case 4:
                    case 5:
                        colorCode = YELLOW;
                        break;

                    case 6:
                    case 7:
                        colorCode = ORANGE;
                        break;

                    case 8:
                    case 9:
                    case 10:
                        colorCode = RED;
                        break;
                }
            }
        }

        return colorCode;
    }
}
