package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Pressure;
import com.waynedgrant.cirrus.units.PressureUnit;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestPressureFormatter
{
    private PressureFormatter testee;
    
    @Test
    public void GIVEN_null_pressure_WHEN_hectopascals_formatting_requested_THEN_returns_blank_hectopascals_value()
    {
        testee = new PressureFormatter(null);
        assertEquals("----.- hPa", testee.format(PressureUnit.HECTOPASCALS));
    }
    
    @Test
    public void GIVEN_null_pressure_WHEN_millibars_formatting_requested_THEN_returns_blank_millibars_value()
    {
        testee = new PressureFormatter(null);
        assertEquals("----.- mb", testee.format(PressureUnit.MILLIBARS));
    }
    
    @Test
    public void GIVEN_null_pressure_WHEN_kilopascals_formatting_requested_THEN_returns_blank_kilopascals_value()
    {
        testee = new PressureFormatter(null);
        assertEquals("---.-- kPa", testee.format(PressureUnit.KILIOPASCALS));
    }
    
    @Test
    public void GIVEN_null_pressure_WHEN_inches_of_mercury_formatting_requested_THEN_returns_blank_inches_of_mercury_value()
    {
        testee = new PressureFormatter(null);
        assertEquals("--.-- inHg", testee.format(PressureUnit.INCHES_OF_MERCURY));
    }
    
    @Test
    public void GIVEN_null_pressure_WHEN_millimetres_of_mercury_formatting_requested_THEN_returns_blank_millimetres_of_mercury_value()
    {
        testee = new PressureFormatter(null);
        assertEquals("---.- mmHg", testee.format(PressureUnit.MILLIMETRES_OF_MERCURY));
    }
    
    @Test
    public void GIVEN_non_null_pressure_WHEN_hectopascals_formatting_requested_THEN_returns_hectopascals_value_to_one_digit()
    {
        testee = new PressureFormatter(new Pressure(new BigDecimal("1015.66")));
        assertEquals("1015.7 hPa", testee.format(PressureUnit.HECTOPASCALS));
        
        testee = new PressureFormatter(new Pressure(new BigDecimal("1015.65")));
        assertEquals("1015.6 hPa", testee.format(PressureUnit.HECTOPASCALS));
    }
    
    @Test
    public void GIVEN_non_null_pressure_WHEN_millibars_formatting_requested_THEN_returns_millibars_value_to_one_digit()
    {
        testee = new PressureFormatter(new Pressure(new BigDecimal("1015.66")));
        assertEquals("1015.7 mb", testee.format(PressureUnit.MILLIBARS));
        
        testee = new PressureFormatter(new Pressure(new BigDecimal("1015.65")));
        assertEquals("1015.6 mb", testee.format(PressureUnit.MILLIBARS));
    }
    
    @Test
    public void GIVEN_non_null_pressure_WHEN_kilopascals_formatting_requested_THEN_returns_kilopascals_value_to_two_digits()
    {
        testee = new PressureFormatter(new Pressure(new BigDecimal("1015.66")));
        assertEquals("101.57 kPa", testee.format(PressureUnit.KILIOPASCALS));
        
        testee = new PressureFormatter(new Pressure(new BigDecimal("1015.65")));
        assertEquals("101.56 kPa", testee.format(PressureUnit.KILIOPASCALS));
    }
    
    @Test
    public void GIVEN_non_null_pressure_WHEN_inches_of_mercury_formatting_requested_THEN_returns_inches_of_mercury_value_to_two_digits()
    {
        testee = new PressureFormatter(new Pressure(new BigDecimal("1013.75"))); // == 29.93604 inHg (round up)
        assertEquals("29.94 inHg", testee.format(PressureUnit.INCHES_OF_MERCURY));
        
        testee = new PressureFormatter(new Pressure(new BigDecimal("1013.25"))); // == 29.92127 inHg (round down)
        assertEquals("29.92 inHg", testee.format(PressureUnit.INCHES_OF_MERCURY));
    }
    
    @Test
    public void GIVEN_non_null_pressure_WHEN_millimetres_of_mercury_formatting_requested_THEN_returns_millimetres_of_mercury_value_to_one_digit()
    {
        testee = new PressureFormatter(new Pressure(new BigDecimal("1013.75"))); // == 760.3754 mmHg (round up)
        assertEquals("760.4 mmHg", testee.format(PressureUnit.MILLIMETRES_OF_MERCURY));
        
        testee = new PressureFormatter(new Pressure(new BigDecimal("1013.11"))); // == 759.9028 mmHg (round down)
        assertEquals("759.9 mmHg", testee.format(PressureUnit.MILLIMETRES_OF_MERCURY));
    }
}
