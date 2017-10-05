package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Rainfall;
import com.waynedgrant.cirrus.units.RainfallUnit;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestRainfallRateFormatter
{
    private RainfallRateFormatter testee;
    
    @Test
    public void GIVEN_null_rainfall_rate_WHEN_millmetres_formatting_requested_THEN_returns_blank_millmetres_value()
    {
        testee = new RainfallRateFormatter(null);
        assertEquals("-.-- mm/min", testee.format(RainfallUnit.MILLIMETRES));
    }
    
    @Test
    public void GIVEN_null_rainfall_rate_WHEN_inches_formatting_requested_THEN_returns_blank_inches_value()
    {
        testee = new RainfallRateFormatter(null);
        assertEquals("-.--- in/min", testee.format(RainfallUnit.INCHES));
    }
    
    @Test
    public void GIVEN_non_null_rainfall_rate_WHEN_millmetres_formatting_requested_THEN_returns_millmetres_value_to_three_digits()
    {
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("5.666")));
        assertEquals("5.67 mm/min", testee.format(RainfallUnit.MILLIMETRES));
        
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("0")));
        assertEquals("0.00 mm/min", testee.format(RainfallUnit.MILLIMETRES));
        
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("0.001")));
        assertEquals("0.00 mm/min", testee.format(RainfallUnit.MILLIMETRES));
        
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("5.655")));
        assertEquals("5.65 mm/min", testee.format(RainfallUnit.MILLIMETRES));
    }
    
    @Test
    public void GIVEN_non_null_rainfall_rate_WHEN_inches_formatting_requested_THEN_returns_inches_value_to_two_digits()
    {
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("8.6"))); // == 0.338583 in (round up)
        assertEquals("0.339 in/min", testee.format(RainfallUnit.INCHES));
        
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("0"))); // == 0 in (zero)
        assertEquals("0.000 in/min", testee.format(RainfallUnit.INCHES));
        
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("0.01"))); // == 0.0003937 in (close to zero)
        assertEquals("0.000 in/min", testee.format(RainfallUnit.INCHES));
        
        testee = new RainfallRateFormatter(new Rainfall(new BigDecimal("3"))); // == 0.11811 in (round down)
        assertEquals("0.118 in/min", testee.format(RainfallUnit.INCHES));
    }
}
