package com.waynedgrant.cirrus.presentation.formatters;

import static junit.framework.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.waynedgrant.cirrus.presentation.formatters.UvIndexFormatter;

public class TestUvIndexFormatter
{
    private UvIndexFormatter testee;
    
    @Test
    public void GIVEN_null_uv_index_WHEN_formatting_requested_THEN_returns_blank_value()
    {
        testee = new UvIndexFormatter(null);
        assertEquals("-.- -----", testee.format());
    }
    
    @Test
    public void GIVEN_uv_index_less_than_2_point_6_degrees_celsius_WHEN_color_requested_THEN_returns_value_to_one_digit_and_low_text()
    {
        testee = new UvIndexFormatter(new BigDecimal("0.0"));
        assertEquals("0.0 low", testee.format());
        
        testee = new UvIndexFormatter(new BigDecimal("2.5"));
        assertEquals("2.5 low", testee.format());
    }
    
    @Test
    public void GIVEN_uv_index_between_2_point_6_and_5_point_5_degrees_celsius_WHEN_color_requested_THEN_returns_value_to_one_digit_and_moderate_text()
    {
        testee = new UvIndexFormatter(new BigDecimal("2.6"));
        assertEquals("2.6 moderate", testee.format());
        
        testee = new UvIndexFormatter(new BigDecimal("5.5"));
        assertEquals("5.5 moderate", testee.format());
    }
    
    @Test
    public void GIVEN_uv_index_between_5_point_6_and_7_point_5_degrees_celsius_WHEN_color_requested_THEN_returns_value_to_one_digit_and_high_text()
    {
        testee = new UvIndexFormatter(new BigDecimal("5.6"));
        assertEquals("5.6 high", testee.format());
        
        testee = new UvIndexFormatter(new BigDecimal("7.5"));
        assertEquals("7.5 high", testee.format());
    }
    
    @Test
    public void GIVEN_uv_index_between_7_point_6_and_10_point_5_degrees_celsius_WHEN_color_requested_THEN_returns_value_to_one_digit_and_very_high_text()
    {
        testee = new UvIndexFormatter(new BigDecimal("7.6"));
        assertEquals("7.6 very high", testee.format());
        
        testee = new UvIndexFormatter(new BigDecimal("10.5"));
        assertEquals("10.5 very high", testee.format());
    }
    
    @Test
    public void GIVEN_uv_index_greater_than_10_point_5_degrees_celsius_WHEN_color_requested_THEN_returns_value_to_one_digit_and_extreme_text()
    {
        testee = new UvIndexFormatter(new BigDecimal("10.6"));
        assertEquals("10.6 extreme", testee.format());
    }
}
