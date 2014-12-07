/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.waynedgrant.cirrus.units.WindSpeedUnit;

public class WindSpeed
{
	private BigDecimal knots;
	private BigDecimal metresPerSecond;
	private BigDecimal kilometresPerHour;
	private BigDecimal milesPerHour;
	private BigDecimal beaufortScale;
	
	public WindSpeed(BigDecimal knots)
	{
		this.knots = knots;
		this.metresPerSecond = knots.multiply(new BigDecimal("0.514444"));
		this.kilometresPerHour = knots.multiply(new BigDecimal("1.852"));
		this.milesPerHour = knots.multiply(new BigDecimal("1.15078"));
		this.beaufortScale = convertKnotsToBeaufortScale(knots);
	}
	
	public BigDecimal getValue(WindSpeedUnit unit)
	{
        BigDecimal value = null;
        
        switch (unit)
        {
            case KNOTS: value = knots; break;
            case METRES_PER_SECOND: value = metresPerSecond; break;
            case KILOMETRES_PER_HOUR: value = kilometresPerHour; break;
            case MILES_PER_HOUR: value = milesPerHour; break;
            case BEAUFORT_SCALE: value = beaufortScale; break;
        }
        
        return value;
	}
	
	private BigDecimal convertKnotsToBeaufortScale(BigDecimal knots)
	{
	    int roundedKnots = knots.setScale(0, RoundingMode.HALF_UP).intValue();
	    
	    int beaufortScale = 0;
	    
	    if (roundedKnots >= 1 && roundedKnots <= 3)
	    {
	        beaufortScale = 1;
	    }
	    else if (roundedKnots >= 4 && roundedKnots <= 6)
	    {
	        beaufortScale = 2;
	    }
        else if (roundedKnots >= 7 && roundedKnots <= 10)
        {
            beaufortScale = 3;
        }
        else if (roundedKnots >= 11 && roundedKnots <= 16)
        {
            beaufortScale = 4;
        }
        else if (roundedKnots >= 17 && roundedKnots <= 21)
        {
            beaufortScale = 5;
        }
        else if (roundedKnots >= 22 && roundedKnots <= 27)
        {
            beaufortScale = 6;
        }
        else if (roundedKnots >= 28 && roundedKnots <= 33)
        {
            beaufortScale = 7;
        }
        else if (roundedKnots >= 34 && roundedKnots <= 40)
        {
            beaufortScale = 8;
        }
        else if (roundedKnots >= 41 && roundedKnots <= 47)
        {
            beaufortScale = 9;
        }
        else if (roundedKnots >= 48 && roundedKnots <= 55)
        {
            beaufortScale = 10;
        }
        else if (roundedKnots >= 56 && roundedKnots <= 63)
        {
            beaufortScale = 11;
        }
        else if (roundedKnots >= 64)
        {
            beaufortScale = 12;
        }
	    
	    return new BigDecimal(beaufortScale);
	}
}
