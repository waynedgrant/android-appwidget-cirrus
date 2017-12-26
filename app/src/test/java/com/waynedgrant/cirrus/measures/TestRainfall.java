package com.waynedgrant.cirrus.measures;

import com.waynedgrant.cirrus.BigDecimalEquals;
import com.waynedgrant.cirrus.units.RainfallUnit;

import org.junit.Test;

import java.math.BigDecimal;

public class TestRainfall {
    private Rainfall testee;

    @Test
    public void GIVEN_populated_with_millmetres_WHEN_millimetres_requested_THEN_returns_correct_value() {
        testee = new Rainfall(new BigDecimal("2.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("2.5"), testee.getValue(RainfallUnit.MILLIMETRES));
    }

    @Test
    public void GIVEN_populated_with_millmetres_WHEN_inches_requested_THEN_returns_correct_value() {
        testee = new Rainfall(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(RainfallUnit.INCHES));

        testee = new Rainfall(new BigDecimal("10"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0.39370080"), testee.getValue(RainfallUnit.INCHES));

        testee = new Rainfall(new BigDecimal("50"));
        new BigDecimalEquals().assertEquals(new BigDecimal("1.96850400"), testee.getValue(RainfallUnit.INCHES));
    }
}
