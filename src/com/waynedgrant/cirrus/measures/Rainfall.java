/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

import static java.math.MathContext.DECIMAL32;

import java.math.BigDecimal;

import com.waynedgrant.cirrus.units.RainfallUnit;

public class Rainfall
{
	private BigDecimal millimetres;
	private BigDecimal inches;
	
	public Rainfall(BigDecimal millmetres)
	{
		this.millimetres = millmetres;
		this.inches = millimetres.multiply(new BigDecimal("1").divide(new BigDecimal("25.4"), DECIMAL32));
	}
	
    public BigDecimal getValue(RainfallUnit unit)
    {
        BigDecimal value = null;
        
        switch(unit)
        {
            case MILLIMETRES: value = millimetres; break;
            case INCHES: value = inches; break;
        }
        
        return value;
    }
}
