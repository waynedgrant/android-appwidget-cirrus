package com.waynedgrant.cirrus.presentation.formatters;

import static junit.framework.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.waynedgrant.cirrus.measures.Temperature;
import com.waynedgrant.cirrus.presentation.formatters.TemperatureFormatter;
import com.waynedgrant.cirrus.units.TemperatureUnit;

public class TestTemperatureFormatter
{
    private TemperatureFormatter testee;
    
    @Test
    public void GIVEN_null_temperature_WHEN_celsius_formatting_requested_THEN_returns_blank_celsius_value()
    {
        testee = new TemperatureFormatter(null);
        assertEquals("--.-¡C", testee.format(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_null_temperature_WHEN_fahrenheit_formatting_requested_THEN_returns_blank_fahrenheit_value()
    {
        testee = new TemperatureFormatter(null);
        assertEquals("--.-¡F", testee.format(TemperatureUnit.FAHRENHEIT));
    }
    
    @Test
    public void GIVEN_non_null_temperature_WHEN_celsius_formatting_requested_THEN_returns_celsius_value_to_one_digit()
    {
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("-15.66")));
        assertEquals("-15.7¡C", testee.format(TemperatureUnit.CELSIUS));
        
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("0")));
        assertEquals("0.0¡C", testee.format(TemperatureUnit.CELSIUS));
        
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("-0.01")));
        assertEquals("0.0¡C", testee.format(TemperatureUnit.CELSIUS));
        
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("15.65")));
        assertEquals("15.6¡C", testee.format(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_non_null_temperature_WHEN_fahrenheit_formatting_requested_THEN_returns_fahrenheit_value_to_one_digit()
    {
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("-9.072"))); // == 15.6704¡F (round up)
        assertEquals("15.7¡F", testee.format(TemperatureUnit.FAHRENHEIT));
        
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("0"))); // == 32¡F (zero)
        assertEquals("32.0¡F", testee.format(TemperatureUnit.FAHRENHEIT));
        
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("-17.78"))); // == -0.0004¡F (close to zero)
        assertEquals("0.0¡F", testee.format(TemperatureUnit.FAHRENHEIT));
        
        testee = new TemperatureFormatter(new Temperature(new BigDecimal("-25.12"))); // == -13.216¡F (round down)
        assertEquals("-13.2¡F", testee.format(TemperatureUnit.FAHRENHEIT));
    }
}
