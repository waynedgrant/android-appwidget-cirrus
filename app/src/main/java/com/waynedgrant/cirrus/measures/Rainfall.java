/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

import com.waynedgrant.cirrus.units.RainfallUnit;

import java.math.BigDecimal;

import static java.math.MathContext.DECIMAL32;

public class Rainfall {
    private BigDecimal millimetres;
    private BigDecimal inches;

    public Rainfall(BigDecimal millimetres) {
        this.millimetres = millimetres;
        this.inches = this.millimetres.multiply(new BigDecimal("1").divide(new BigDecimal("25.4"), DECIMAL32));
    }

    public BigDecimal getValue(RainfallUnit unit) {
        BigDecimal value = null;

        switch (unit) {
            case MILLIMETRES:
                value = millimetres;
                break;
            case INCHES:
                value = inches;
                break;
        }

        return value;
    }
}
