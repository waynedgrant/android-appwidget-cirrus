package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Conditions;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestConditionsFormatter
{
    private ConditionsFormatter testee;
    
    @Test
    public void GIVEN_null_conditions_WHEN_formatting_requested_THEN_returns_blank_value()
    {
        testee = new ConditionsFormatter(null);
        assertEquals("-", testee.format());
    }
    
    @Test
    public void GIVEN_conditions_WHEN_formatting_requested_THEN_returns_conditions_description()
    {
        testee = new ConditionsFormatter(Conditions.CLEAR_NIGHT);
        assertEquals("Clear Night", testee.format());
    }
}
