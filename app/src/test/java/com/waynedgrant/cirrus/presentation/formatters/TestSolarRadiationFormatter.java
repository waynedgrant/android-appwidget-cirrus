package com.waynedgrant.cirrus.presentation.formatters;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestSolarRadiationFormatter {
    private SolarRadiationFormatter testee;

    @Test
    public void GIVEN_null_solar_radiation_WHEN_formatting_requested_THEN_returns_blank_value() {
        testee = new SolarRadiationFormatter(null);
        assertEquals("----.- W/m\u00b2", testee.format());
    }

    @Test
    public void GIVEN_non_null_solar_radiation_WHEN_formatting_requested_THEN_returns_value_to_one_digit() {
        testee = new SolarRadiationFormatter(new BigDecimal("0"));
        assertEquals("0.0 W/m\u00b2", testee.format());

        testee = new SolarRadiationFormatter(new BigDecimal("1342.2"));
        assertEquals("1342.2 W/m\u00b2", testee.format());
    }
}
