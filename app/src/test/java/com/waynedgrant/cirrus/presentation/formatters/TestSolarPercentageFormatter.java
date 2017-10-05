package com.waynedgrant.cirrus.presentation.formatters;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestSolarPercentageFormatter
{
    private SolarPercentageFormatter testee;
    
    @Test
    public void GIVEN_null_solar_percentage_WHEN_formatting_requested_THEN_returns_blank_percentage_value()
    {
        testee = new SolarPercentageFormatter(null);
        assertEquals("--%", testee.format());
    }
    
    @Test
    public void GIVEN_non_null_solar_percentage_WHEN_formatting_requested_THEN_returns_correct_percentage_value()
    {
        testee = new SolarPercentageFormatter(0);
        assertEquals("0%", testee.format());
        
        testee = new SolarPercentageFormatter(99);
        assertEquals("99%", testee.format());
    }
}
