package com.waynedgrant.cirrus.measures;

import java.math.BigDecimal;

import org.junit.Test;

import com.waynedgrant.cirrus.BigDecimalEquals;
import com.waynedgrant.cirrus.units.PressureUnit;

public class TestPressure
{
    private Pressure testee;
    
    @Test
    public void GIVEN_populated_with_hectopascals_WHEN_hectopascals_requested_THEN_returns_correct_value()
    {
        testee = new Pressure(new BigDecimal("1017.7"));
        new BigDecimalEquals().assertEquals(new BigDecimal("1017.7"), testee.getValue(PressureUnit.HECTOPASCALS));
    }
    
    @Test
    public void GIVEN_populated_with_hectopascals_WHEN_millibars_requested_THEN_returns_correct_value()
    {
        testee = new Pressure(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(PressureUnit.MILLIBARS));
        
        testee = new Pressure(new BigDecimal("1017.7"));
        new BigDecimalEquals().assertEquals(new BigDecimal("1017.7"), testee.getValue(PressureUnit.MILLIBARS));
    }
    
    @Test
    public void GIVEN_populated_with_hectopascals_WHEN_kilopascals_requested_THEN_returns_correct_value()
    {
        testee = new Pressure(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(PressureUnit.KILIOPASCALS));
        
        testee = new Pressure(new BigDecimal("1017.7"));
        new BigDecimalEquals().assertEquals(new BigDecimal("101.77"), testee.getValue(PressureUnit.KILIOPASCALS));
    }
    
    @Test
    public void GIVEN_populated_with_hectopascals_WHEN_inches_of_mercury_requested_THEN_returns_correct_value()
    {
        testee = new Pressure(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(PressureUnit.INCHES_OF_MERCURY));
        
        testee = new Pressure(new BigDecimal("1017.7"));
        new BigDecimalEquals().assertEquals(new BigDecimal("30.05268"), testee.getValue(PressureUnit.INCHES_OF_MERCURY));
    }
    
    @Test
    public void GIVEN_populated_with_hectopascals_WHEN_millimetres_of_mercury_requested_THEN_returns_correct_value()
    {
        testee = new Pressure(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(PressureUnit.MILLIMETRES_OF_MERCURY));
        
        testee = new Pressure(new BigDecimal("1017.7"));
        new BigDecimalEquals().assertEquals(new BigDecimal("763.3381"), testee.getValue(PressureUnit.MILLIMETRES_OF_MERCURY));
    }
}
