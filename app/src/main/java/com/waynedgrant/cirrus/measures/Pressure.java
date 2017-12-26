/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

import com.waynedgrant.cirrus.units.PressureUnit;

import java.math.BigDecimal;

import static java.math.MathContext.DECIMAL32;

public class Pressure {
    private BigDecimal hectopascals;
    private BigDecimal millibars;
    private BigDecimal kilopascals;
    private BigDecimal inchesOfMercury;
    private BigDecimal millimetresOfMercury;

    public Pressure(BigDecimal hectopascals) {
        this.hectopascals = hectopascals;
        this.millibars = hectopascals;
        this.kilopascals = hectopascals.divide(new BigDecimal("10"), DECIMAL32);
        this.inchesOfMercury = hectopascals.multiply(new BigDecimal("0.02953"), DECIMAL32);
        this.millimetresOfMercury = hectopascals.multiply(new BigDecimal("0.750062"), DECIMAL32);
    }

    public BigDecimal getValue(PressureUnit unit) {
        BigDecimal value = null;

        switch (unit) {
            case HECTOPASCALS:
                value = hectopascals;
                break;
            case MILLIBARS:
                value = millibars;
                break;
            case KILIOPASCALS:
                value = kilopascals;
                break;
            case INCHES_OF_MERCURY:
                value = inchesOfMercury;
                break;
            case MILLIMETRES_OF_MERCURY:
                value = millimetresOfMercury;
                break;
        }

        return value;
    }
}
