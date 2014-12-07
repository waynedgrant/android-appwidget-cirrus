package com.waynedgrant.cirrus.presentation.formatters;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.waynedgrant.cirrus.measures.WindDirection;
import com.waynedgrant.cirrus.presentation.formatters.WindDirectionFormatter;
import com.waynedgrant.cirrus.units.WindDirectionUnit;

public class TestWindDirectionFormatter
{
    private WindDirectionFormatter testee;
    
    @Test
    public void GIVEN_null_wind_direction_WHEN_compass_degrees_formatting_requested_THEN_returns_blank_compass_degrees_value()
    {
        testee = new WindDirectionFormatter(null);
        assertEquals("---°", testee.format(WindDirectionUnit.COMPASS_DEGREES));
    }
    
    @Test
    public void GIVEN_null_wind_direction_WHEN_cardinal_direction_formatting_requested_THEN_returns_blank_cardinal_direction_value()
    {
        testee = new WindDirectionFormatter(null);
        assertEquals("---", testee.format(WindDirectionUnit.CARDINAL_DIRECTION));
    }
    
    @Test
    public void GIVEN_non_null_wind_direction_WHEN_compass_degrees_formatting_requested_THEN_returns_compass_degrees_value()
    {
        testee = new WindDirectionFormatter(new WindDirection(0));
        assertEquals("0°", testee.format(WindDirectionUnit.COMPASS_DEGREES));
        
        testee = new WindDirectionFormatter(new WindDirection(338));
        assertEquals("338°", testee.format(WindDirectionUnit.COMPASS_DEGREES));
    }
    
    @Test
    public void GIVEN_non_null_wind_direction_WHEN_cardinal_direction_formatting_requested_THEN_returns_cardinal_direction_value()
    {
        testee = new WindDirectionFormatter(new WindDirection(0));
        assertEquals("N", testee.format(WindDirectionUnit.CARDINAL_DIRECTION));
        
        testee = new WindDirectionFormatter(new WindDirection(338));
        assertEquals("NNW", testee.format(WindDirectionUnit.CARDINAL_DIRECTION));
    }
}
