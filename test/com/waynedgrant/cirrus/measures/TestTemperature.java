package com.waynedgrant.cirrus.measures;

import static junit.framework.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.waynedgrant.cirrus.BigDecimalEquals;
import com.waynedgrant.cirrus.units.TemperatureUnit;

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
