package com.waynedgrant.cirrus.presentation.colorizers;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestUvIndexColorizer
{
    private UvIndexColorizer testee;
    
    @Test
    public void GIVEN_null_uv_index_WHEN_color_requested_THEN_white_color_returned()
    {
        testee = new UvIndexColorizer(null);
        assertEquals(UvIndexColorizer.WHITE, testee.colorize());
    }
    
    @Test
    public void GIVEN_uv_index_less_than_2_point_6_degrees_celsius_WHEN_color_requested_THEN_green_color_returned()
    {
        testee = new UvIndexColorizer(new BigDecimal("0.0"));
        assertEquals(UvIndexColorizer.GREEN, testee.colorize());
        
        testee = new UvIndexColorizer(new BigDecimal("2.5"));
        assertEquals(UvIndexColorizer.GREEN, testee.colorize());
    }
    
    @Test
    public void GIVEN_uv_index_between_2_point_6_and_5_point_5_degrees_celsius_WHEN_color_requested_THEN_yellow_color_returned()
    {
        testee = new UvIndexColorizer(new BigDecimal("2.6"));
        assertEquals(UvIndexColorizer.YELLOW, testee.colorize());
        
        testee = new UvIndexColorizer(new BigDecimal("5.5"));
        assertEquals(UvIndexColorizer.YELLOW, testee.colorize());
    }
    
    @Test
    public void GIVEN_uv_index_between_5_point_6_and_7_point_5_degrees_celsius_WHEN_color_requested_THEN_orange_color_returned()
    {
        testee = new UvIndexColorizer(new BigDecimal("5.6"));
        assertEquals(UvIndexColorizer.ORANGE, testee.colorize());
        
        testee = new UvIndexColorizer(new BigDecimal("7.5"));
        assertEquals(UvIndexColorizer.ORANGE, testee.colorize());
    }
    
    @Test
    public void GIVEN_uv_index_between_7_point_6_and_10_point_5_degrees_celsius_WHEN_color_requested_THEN_red_color_returned()
    {
        testee = new UvIndexColorizer(new BigDecimal("7.6"));
        assertEquals(UvIndexColorizer.RED, testee.colorize());
        
        testee = new UvIndexColorizer(new BigDecimal("10.5"));
        assertEquals(UvIndexColorizer.RED, testee.colorize());
    }
    
    @Test
    public void GIVEN_uv_index_greater_than_10_point_5_degrees_celsius_WHEN_color_requested_THEN_purple_color_returned()
    {
        testee = new UvIndexColorizer(new BigDecimal("10.6"));
        assertEquals(UvIndexColorizer.PURPLE, testee.colorize());
    }
}
