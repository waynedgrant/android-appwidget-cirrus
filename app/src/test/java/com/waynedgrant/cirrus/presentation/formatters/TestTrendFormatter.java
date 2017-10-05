package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Trend;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestTrendFormatter
{
    private TrendFormatter testee;
    
    @Test
    public void GIVEN_null_trend_WHEN_formatting_requested_THEN_returns_blank_character()
    {
        testee = new TrendFormatter(null);
        assertEquals("-", testee.format());
    }
    
    @Test
    public void GIVEN_rising_trend_WHEN_formatting_requested_THEN_returns_rising_character()
    {
        testee = new TrendFormatter(Trend.RISING);
        assertEquals("\u279A", testee.format());
    }
    
    @Test
    public void GIVEN_steady_trend_WHEN_formatting_requested_THEN_returns_steady_character()
    {
        testee = new TrendFormatter(Trend.STEADY);
        assertEquals("\u279E", testee.format());
    }
 
    @Test
    public void GIVEN_falling_trend_WHEN_formatting_requested_THEN_returns_falling_character()
    {
        testee = new TrendFormatter(Trend.FALLING);
        assertEquals("\u2798", testee.format());
    }
}
