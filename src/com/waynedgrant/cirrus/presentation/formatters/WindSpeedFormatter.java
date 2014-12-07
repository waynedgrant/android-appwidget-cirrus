/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import static com.waynedgrant.cirrus.units.WindSpeedUnit.BEAUFORT_SCALE;
import static com.waynedgrant.cirrus.units.WindSpeedUnit.KILOMETRES_PER_HOUR;
import static com.waynedgrant.cirrus.units.WindSpeedUnit.KNOTS;
import static com.waynedgrant.cirrus.units.WindSpeedUnit.METRES_PER_SECOND;
import static com.waynedgrant.cirrus.units.WindSpeedUnit.MILES_PER_HOUR;

import java.math.RoundingMode;
import java.util.Locale;

import com.waynedgrant.cirrus.measures.WindSpeed;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

public class WindSpeedFormatter
{
    private WindSpeed windSpeed;
    
    public WindSpeedFormatter(WindSpeed windSpeed)
    {
        this.windSpeed = windSpeed;
    }
    
    public String format(WindSpeedUnit unit)
    {
        String formatted = null;
        
        if (windSpeed != null)
        {
            String formatString = null;
            
            if (unit == BEAUFORT_SCALE)
            {
                formatted = String.format(Locale.US, "%1d Bft", windSpeed.getValue(unit).intValue());
            }
            else
            {
                if (unit == KNOTS)
                {
                    formatString = "%1.1f kts";
                }
                else if (unit == METRES_PER_SECOND)
                {
                    formatString = "%1.1f m/s";
                }
                else if (unit == KILOMETRES_PER_HOUR)
                {
                    formatString = "%1.1f kph";
                }
                else if (unit == MILES_PER_HOUR)
                {
                    formatString = "%1.1f mph";
                }
                
                formatted = String.format(Locale.US, formatString, windSpeed.getValue(unit).setScale(1, RoundingMode.HALF_DOWN));
            }
        }
        else
        {
            if (unit == KNOTS)
            {
                formatted = "--.- kts";
            }
            else if (unit == METRES_PER_SECOND)
            {
                formatted = "--.- m/s";
            }
            else if (unit == KILOMETRES_PER_HOUR)
            {
                formatted = "--.- kph";
            }
            else if (unit == MILES_PER_HOUR)
            {
                formatted = "--.- mph";
            }
            else if (unit == BEAUFORT_SCALE)
            {
                formatted = "-- Bft";
            }
        }
        
        return formatted;
    }
}
