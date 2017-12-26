package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Rainfall;
import com.waynedgrant.cirrus.units.RainfallUnit;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestRainfallFormatter {
    private RainfallFormatter testee;

    @Test
    public void GIVEN_null_rainfall_WHEN_millmetres_formatting_requested_THEN_returns_blank_millmetres_value() {
        testee = new RainfallFormatter(null);
        assertEquals("-.-- mm", testee.format(RainfallUnit.MILLIMETRES));
    }

    @Test
    public void GIVEN_null_rainfall_WHEN_inches_formatting_requested_THEN_returns_blank_inches_value() {
        testee = new RainfallFormatter(null);
        assertEquals("-.-- in", testee.format(RainfallUnit.INCHES));
    }

    @Test
    public void GIVEN_non_null_rainfall_WHEN_millmetres_formatting_requested_THEN_returns_millmetres_value_to_two_digits() {
        testee = new RainfallFormatter(new Rainfall(new BigDecimal("5.666")));
        assertEquals("5.67 mm", testee.format(RainfallUnit.MILLIMETRES));

        testee = new RainfallFormatter(new Rainfall(new BigDecimal("0")));
        assertEquals("0.00 mm", testee.format(RainfallUnit.MILLIMETRES));

        testee = new RainfallFormatter(new Rainfall(new BigDecimal("0.001")));
        assertEquals("0.00 mm", testee.format(RainfallUnit.MILLIMETRES));

        testee = new RainfallFormatter(new Rainfall(new BigDecimal("5.655")));
        assertEquals("5.65 mm", testee.format(RainfallUnit.MILLIMETRES));
    }

    @Test
    public void GIVEN_non_null_rainfall_WHEN_inches_formatting_requested_THEN_returns_inches_value_to_two_digits() {
        testee = new RainfallFormatter(new Rainfall(new BigDecimal("3"))); // == 0.11811 in (round up)
        assertEquals("0.12 in", testee.format(RainfallUnit.INCHES));

        testee = new RainfallFormatter(new Rainfall(new BigDecimal("0"))); // == 0 in (zero)
        assertEquals("0.00 in", testee.format(RainfallUnit.INCHES));

        testee = new RainfallFormatter(new Rainfall(new BigDecimal("0.1"))); // == 0.003937 in (close to zero)
        assertEquals("0.00 in", testee.format(RainfallUnit.INCHES));

        testee = new RainfallFormatter(new Rainfall(new BigDecimal("8.5"))); // == 0.334645 in (round down)
        assertEquals("0.33 in", testee.format(RainfallUnit.INCHES));
    }
}
