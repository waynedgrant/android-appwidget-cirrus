/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

import java.math.BigDecimal;

import com.waynedgrant.cirrus.units.TemperatureUnit;

public class Temperature
{
	private BigDecimal celsius;
	private BigDecimal fahrenheit;
	
	public Temperature(BigDecimal celsius)
	{
		this.celsius = celsius;
		this.fahrenheit = (celsius.multiply(new BigDecimal("1.8"))).add(new BigDecimal("32"));
	}
	
	public BigDecimal getValue(TemperatureUnit unit)
	{
	    BigDecimal value = null;
	    
	    switch (unit)
	    {
	        case CELSIUS: value = celsius; break;
	        case FAHRENHEIT: value = fahrenheit; break;
	    }
	    
	    return value;
	}
}
