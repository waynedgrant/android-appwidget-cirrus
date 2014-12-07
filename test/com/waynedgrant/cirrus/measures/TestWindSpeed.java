package com.waynedgrant.cirrus.measures;

import java.math.BigDecimal;

import org.junit.Test;

import com.waynedgrant.cirrus.BigDecimalEquals;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

public class TestWindSpeed
{
	private WindSpeed testee;
	
	@Test
	public void GIVEN_populated_with_knots_WHEN_knots_requested_THEN_returns_correct_value()
	{
		testee = new WindSpeed(new BigDecimal("1.5"));
		new BigDecimalEquals().assertEquals(new BigDecimal("1.5"), testee.getValue(WindSpeedUnit.KNOTS));
	}
	
	@Test
	public void GIVEN_populated_with_knots_WHEN_metres_per_second_requested_THEN_returns_correct_value()
	{
		testee = new WindSpeed(new BigDecimal("0"));
		new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(WindSpeedUnit.METRES_PER_SECOND));
		
		testee = new WindSpeed(new BigDecimal("5"));
		new BigDecimalEquals().assertEquals(new BigDecimal("2.57222"), testee.getValue(WindSpeedUnit.METRES_PER_SECOND));
	}
	
	@Test
	public void GIVEN_populated_with_knots_WHEN_get_kilometres_per_hour_requested_THEN_returns_correct_value()
	{
		testee = new WindSpeed(new BigDecimal("0"));
		new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(WindSpeedUnit.KILOMETRES_PER_HOUR));
		
		testee = new WindSpeed(new BigDecimal("5"));
		new BigDecimalEquals().assertEquals(new BigDecimal("9.26"), testee.getValue(WindSpeedUnit.KILOMETRES_PER_HOUR));
	}
	
	@Test
	public void GIVEN_populated_with_knots_WHEN_get_miles_per_hour_requested_THEN_returns_correct_value()
	{
		testee = new WindSpeed(new BigDecimal("0"));
		new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(WindSpeedUnit.MILES_PER_HOUR));
		
		testee = new WindSpeed(new BigDecimal("5"));
		new BigDecimalEquals().assertEquals(new BigDecimal("5.7539"), testee.getValue(WindSpeedUnit.MILES_PER_HOUR));
	}
	
    @Test
    public void GIVEN_populated_with_knots_between_0_and_0_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_0()
    {
        testee = new WindSpeed(new BigDecimal("0"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("0.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("0"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_0_point_5_and_3_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_1()
    {
        testee = new WindSpeed(new BigDecimal("0.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("1"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("3.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("1"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_3_point_5_and_6_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_2()
    {
        testee = new WindSpeed(new BigDecimal("3.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("2"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("6.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("2"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_6_point_5_and_10_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_3()
    {
        testee = new WindSpeed(new BigDecimal("6.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("3"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("10.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("3"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_10_point_5_and_16_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_4()
    {
        testee = new WindSpeed(new BigDecimal("10.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("4"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("16.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("4"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }

    @Test
    public void GIVEN_populated_with_knots_between_16_point_5_and_21_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_5()
    {
        testee = new WindSpeed(new BigDecimal("16.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("5"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("21.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("5"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_21_point_5_and_27_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_6()
    {
        testee = new WindSpeed(new BigDecimal("21.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("6"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("27.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("6"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_27_point_5_and_33_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_7()
    {
        testee = new WindSpeed(new BigDecimal("27.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("7"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("33.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("7"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }

    @Test
    public void GIVEN_populated_with_knots_between_33_point_5_and_40_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_8()
    {
        testee = new WindSpeed(new BigDecimal("33.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("8"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("40.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("8"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_40_point_5_and_47_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_9()
    {
        testee = new WindSpeed(new BigDecimal("40.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("9"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("47.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("9"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_47_point_5_and_55_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_10()
    {
        testee = new WindSpeed(new BigDecimal("47.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("10"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("55.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("10"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_between_55_point_5_and_63_point_4_WHEN_get_beaufort_scale_requested_THEN_returns_11()
    {
        testee = new WindSpeed(new BigDecimal("55.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("11"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeed(new BigDecimal("63.4"));
        new BigDecimalEquals().assertEquals(new BigDecimal("11"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_populated_with_knots_greater_than_or_equal_to_63_point_5_WHEN_get_beaufort_scale_requested_THEN_returns_12()
    {
        testee = new WindSpeed(new BigDecimal("63.5"));
        new BigDecimalEquals().assertEquals(new BigDecimal("12"), testee.getValue(WindSpeedUnit.BEAUFORT_SCALE));
    }
}
