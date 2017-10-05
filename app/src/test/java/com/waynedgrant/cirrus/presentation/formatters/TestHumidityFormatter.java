package com.waynedgrant.cirrus.presentation.formatters;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestHumidityFormatter
{
    private HumidityFormatter testee;
    
    @Test
    public void GIVEN_null_humidity_WHEN_formatting_requested_THEN_returns_blank_percentage_value()
    {
        testee = new HumidityFormatter(null);
        assertEquals("--%", testee.format());
    }
    
    @Test
    public void GIVEN_non_null_humidity_WHEN_formatting_requested_THEN_returns_correct_percentage_value()
    {
        testee = new HumidityFormatter(0);
        assertEquals("0%", testee.format());
        
        testee = new HumidityFormatter(99);
        assertEquals("99%", testee.format());
    }
}
