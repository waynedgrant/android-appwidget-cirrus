package com.waynedgrant.cirrus.presentation.formatters;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class TestTimeFormatter
{
    private TimeFormatter testee;
    
    @Test
    public void GIVEN_null_time_component_WHEN_24_hour_formatting_requested_THEN_returns_empty_time_value()
    {
        testee = new TimeFormatter(null, 59);
        assertEquals("--:--", testee.format(TimeFormat.HOUR_24));
        
        testee = new TimeFormatter(23, null);
        assertEquals("--:--", testee.format(TimeFormat.HOUR_24));
    }
    
    @Test
    public void GIVEN_null_time_component_WHEN_12_hour_formatting_requested_THEN_returns_empty_time_value()
    {
        testee = new TimeFormatter(null, 59);
        assertEquals("--:-- --", testee.format(TimeFormat.HOUR_12));
        
        testee = new TimeFormatter(23, null);
        assertEquals("--:-- --", testee.format(TimeFormat.HOUR_12));
    }
    
    @Test
    public void GIVEN_non_null_time_components_WHEN_24_hour_formatting_requested_THEN_returns_populated_time_value()
    {
        testee = new TimeFormatter(0, 0);
        assertEquals("00:00", testee.format(TimeFormat.HOUR_24));
        
        testee = new TimeFormatter(1, 2);
        assertEquals("01:02", testee.format(TimeFormat.HOUR_24));
        
        testee = new TimeFormatter(12, 0);
        assertEquals("12:00", testee.format(TimeFormat.HOUR_24));
        
        testee = new TimeFormatter(23, 59);
        assertEquals("23:59", testee.format(TimeFormat.HOUR_24));
    }
    
    @Test
    public void GIVEN_non_null_time_components_WHEN_12_hour_formatting_requested_THEN_returns_populated_time_value()
    {
        testee = new TimeFormatter(0, 0);
        assertEquals("12:00 AM", testee.format(TimeFormat.HOUR_12));
        
        testee = new TimeFormatter(1, 2);
        assertEquals("1:02 AM", testee.format(TimeFormat.HOUR_12));
        
        testee = new TimeFormatter(12, 0);
        assertEquals("12:00 PM", testee.format(TimeFormat.HOUR_12));
        
        testee = new TimeFormatter(23, 59);
        assertEquals("11:59 PM", testee.format(TimeFormat.HOUR_12));
    }
}
