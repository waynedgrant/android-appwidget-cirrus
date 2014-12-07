/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class UvIndexFormatter
{
    private BigDecimal uvIndex;
    
    public UvIndexFormatter(BigDecimal uvIndex)
    {
        this.uvIndex = uvIndex;
    }

    public String format()
    {
        // 0-2 low, 3-5 moderate, 6-7 high, 8-10 very high, 11+ extreme
        
        String formatted = "-.- -----";
        
        if (uvIndex != null)
        {
            int uvIndexRounded = uvIndex.setScale(0, RoundingMode.HALF_DOWN).intValue();

            String description = null;
            
            if (uvIndexRounded < 3)
            {
                description = "low";
            }
            else if (uvIndexRounded > 10)
            {
                description = "extreme";
            }
            else
            {      
                switch (uvIndexRounded)
                {
                    case 3:
                    case 4:
                    case 5: description = "moderate"; break;
                    
                    case 6:
                    case 7: description = "high"; break;
                    
                    case 8:
                    case 9:
                    case 10: description = "very high"; break;
                }
            }
            
            formatted = String.format(Locale.US, "%1.1f %2s", uvIndex.setScale(1, RoundingMode.HALF_DOWN), description);
        }
        
        return formatted;
    }
}
