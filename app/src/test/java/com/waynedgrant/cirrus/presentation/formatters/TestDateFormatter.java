package com.waynedgrant.cirrus.presentation.formatters;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.waynedgrant.cirrus.presentation.formatters.DateFormat;
import com.waynedgrant.cirrus.presentation.formatters.DateFormatter;

public class TestDateFormatter
{
    private DateFormatter testee;
    
    @Test
    public void GIVEN_null_date_component_WHEN_yyyymm_formatting_requested_THEN_returns_empty_date_value()
    {
        testee = new DateFormatter(null, 12, 31);
        assertEquals("----/--/--", testee.format(DateFormat.YYYY_MM_DD));
        
        testee = new DateFormatter(2013, null, 31);
        assertEquals("----/--/--", testee.format(DateFormat.YYYY_MM_DD));

        testee = new DateFormatter(2013, 12, null);
        assertEquals("----/--/--", testee.format(DateFormat.YYYY_MM_DD));
    }
    
    @Test
    public void GIVEN_null_date_component_WHEN_ddmmyyyy_formatting_requested_THEN_returns_empty_date_value()
    {
        testee = new DateFormatter(null, 12, 31);
        assertEquals("--/--/----", testee.format(DateFormat.DD_MM_YYYY));
        
        testee = new DateFormatter(2013, null, 31);
        assertEquals("--/--/----", testee.format(DateFormat.DD_MM_YYYY));

        testee = new DateFormatter(2013, 12, null);
        assertEquals("--/--/----", testee.format(DateFormat.DD_MM_YYYY));
    }
    
    @Test
    public void GIVEN_null_date_component_WHEN_mmddyyyy_formatting_requested_THEN_returns_empty_date_value()
    {
        testee = new DateFormatter(null, 12, 31);
        assertEquals("--/--/----", testee.format(DateFormat.MM_DD_YYYY));
        
        testee = new DateFormatter(2013, null, 31);
        assertEquals("--/--/----", testee.format(DateFormat.MM_DD_YYYY));

        testee = new DateFormatter(2013, 12, null);
        assertEquals("--/--/----", testee.format(DateFormat.MM_DD_YYYY));
    }
    
    @Test
    public void GIVEN_non_null_date_components_WHEN_yyyymmdd_formatting_requested_THEN_returns_populated_date_value()
    {
        testee = new DateFormatter(2013, 12, 31);
        assertEquals("2013/12/31", testee.format(DateFormat.YYYY_MM_DD));
    }
    
    @Test
    public void GIVEN_non_null_date_components_WHEN_ddmmyyyy_formatting_requested_THEN_returns_populated_date_value()
    {
        testee = new DateFormatter(2013, 12, 31);
        assertEquals("31/12/2013", testee.format(DateFormat.DD_MM_YYYY));
    }
    
    @Test
    public void GIVEN_non_null_date_components_WHEN_mmddyyyy_formatting_requested_THEN_returns_populated_date_value()
    {
        testee = new DateFormatter(2013, 12, 31);
        assertEquals("12/31/2013", testee.format(DateFormat.MM_DD_YYYY));
    }
}
