package com.waynedgrant.cirrus.measures;

import com.waynedgrant.cirrus.BigDecimalEquals;
import com.waynedgrant.cirrus.units.TemperatureUnit;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestTemperature
{
    private Temperature testee;
    
    @Test
    public void GIVEN_populated_with_celsius_WHEN_celsius_requested_THEN_returns_correct_value()
    {
        testee = new Temperature(new BigDecimal("25.5"));
        assertEquals(new BigDecimal("25.5"), testee.getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_populated_with_celsius_WHEN_fahrenheit_requested_THEN_returns_correct_value()
    {
        testee = new Temperature(new BigDecimal("-50.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("-58.9"), testee.getValue(TemperatureUnit.FAHRENHEIT));
        
        testee = new Temperature(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("32"), testee.getValue(TemperatureUnit.FAHRENHEIT));
        
        testee = new Temperature(new BigDecimal("50.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("122.9"), testee.getValue(TemperatureUnit.FAHRENHEIT));
    }
}
