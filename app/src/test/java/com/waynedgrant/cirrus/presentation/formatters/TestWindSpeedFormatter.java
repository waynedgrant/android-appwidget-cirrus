package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.WindSpeed;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestWindSpeedFormatter
{
    private WindSpeedFormatter testee;
    
    @Test
    public void GIVEN_null_wind_speed_WHEN_knots_formatting_requested_THEN_returns_blank_knots_value()
    {
        testee = new WindSpeedFormatter(null);
        assertEquals("--.- kts", testee.format(WindSpeedUnit.KNOTS));
    }
    
    @Test
    public void GIVEN_null_wind_speed_WHEN_metres_per_second_formatting_requested_THEN_returns_blank_metres_per_second_value()
    {
        testee = new WindSpeedFormatter(null);
        assertEquals("--.- m/s", testee.format(WindSpeedUnit.METRES_PER_SECOND));
    }
    
    @Test
    public void GIVEN_null_wind_speed_WHEN_kilometres_per_hour_formatting_requested_THEN_returns_blank_kilometres_per_hour_value()
    {
        testee = new WindSpeedFormatter(null);
        assertEquals("--.- km/h", testee.format(WindSpeedUnit.KILOMETRES_PER_HOUR));
    }
    
    @Test
    public void GIVEN_null_wind_speed_WHEN_miles_per_hour_formatting_requested_THEN_returns_blank_miles_per_hour_value()
    {
        testee = new WindSpeedFormatter(null);
        assertEquals("--.- mph", testee.format(WindSpeedUnit.MILES_PER_HOUR));
    }
    
    @Test
    public void GIVEN_null_wind_speed_WHEN_beaufort_scale_formatting_requested_THEN_returns_blank_beaufort_scale_value()
    {
        testee = new WindSpeedFormatter(null);
        assertEquals("-- Bft", testee.format(WindSpeedUnit.BEAUFORT_SCALE));
    }
    
    @Test
    public void GIVEN_non_null_wind_speed_WHEN_knots_formatting_requested_THEN_returns_knots_value_to_one_digit()
    {
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("15.66")));
        assertEquals("15.7 kts", testee.format(WindSpeedUnit.KNOTS));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0")));
        assertEquals("0.0 kts", testee.format(WindSpeedUnit.KNOTS));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0.01")));
        assertEquals("0.0 kts", testee.format(WindSpeedUnit.KNOTS));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("15.65")));
        assertEquals("15.6 kts", testee.format(WindSpeedUnit.KNOTS));
    }
    
    @Test
    public void GIVEN_non_null_wind_speed_WHEN_metres_per_second_formatting_requested_THEN_returns_metres_per_second_value_to_one_digit()
    {
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("14.5"))); // == 7.459 m/s (round up)
        assertEquals("7.5 m/s", testee.format(WindSpeedUnit.METRES_PER_SECOND));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0"))); // == 0 m/s (zero)
        assertEquals("0.0 m/s", testee.format(WindSpeedUnit.METRES_PER_SECOND));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0.01"))); // == 0.0514 m/s (round to zero)
        assertEquals("0.0 m/s", testee.format(WindSpeedUnit.METRES_PER_SECOND));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("14.67603"))); // == 7.55 m/s (round down)
        assertEquals("7.5 m/s", testee.format(WindSpeedUnit.METRES_PER_SECOND));
    }
    
    @Test
    public void GIVEN_non_null_wind_speed_WHEN_kilometres_per_hour_formatting_requested_THEN_returns_kilometres_per_hour_value_to_one_digit()
    {
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("1"))); // == 1.852 km/h (round up)
        assertEquals("1.9 km/h", testee.format(WindSpeedUnit.KILOMETRES_PER_HOUR));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0"))); // == 0 km/h (zero)
        assertEquals("0.0 km/h", testee.format(WindSpeedUnit.KILOMETRES_PER_HOUR));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0.01"))); // == 0.01852 km/h (round to zero)
        assertEquals("0.0 km/h", testee.format(WindSpeedUnit.KILOMETRES_PER_HOUR));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0.5"))); // == 0.926 km/h (round down)
        assertEquals("0.9 km/h", testee.format(WindSpeedUnit.KILOMETRES_PER_HOUR));
    }
    
    @Test
    public void GIVEN_non_null_wind_speed_WHEN_miles_per_hour_formatting_requested_THEN_returns_miles_per_hour_value_to_one_digit()
    {
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("13.55"))); // == 15.593 mph (round up)
        assertEquals("15.6 mph", testee.format(WindSpeedUnit.MILES_PER_HOUR));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0"))); // == 0 mph (zero)
        assertEquals("0.0 mph", testee.format(WindSpeedUnit.MILES_PER_HOUR));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0.01"))); // == 0.0115 mph (round to zero)
        assertEquals("0.0 mph", testee.format(WindSpeedUnit.MILES_PER_HOUR));
      
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("13.4"))); // == 15.42 mph (round down)
        assertEquals("15.4 mph", testee.format(WindSpeedUnit.MILES_PER_HOUR));
    }
    
    @Test
    public void GIVEN_non_null_wind_speed_WHEN_beaufort_scale_formatting_requested_THEN_returns_beaufort_value()
    {
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("0")));
        assertEquals("0 Bft", testee.format(WindSpeedUnit.BEAUFORT_SCALE));
        
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("25")));
        assertEquals("6 Bft", testee.format(WindSpeedUnit.BEAUFORT_SCALE));
      
        testee = new WindSpeedFormatter(new WindSpeed(new BigDecimal("64")));
        assertEquals("12 Bft", testee.format(WindSpeedUnit.BEAUFORT_SCALE));
    }
}
