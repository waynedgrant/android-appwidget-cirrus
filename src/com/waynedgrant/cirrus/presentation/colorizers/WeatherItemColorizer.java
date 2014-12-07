/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.colorizers;

import com.waynedgrant.cirrus.clientraw.ClientRaw;
import com.waynedgrant.cirrus.measures.WeatherItem;

public class WeatherItemColorizer
{
    static final int WHITE = 0xffffffff;

    private ClientRaw clientRaw;

    public WeatherItemColorizer(ClientRaw clientRaw)
    {
        this.clientRaw = clientRaw;
    }
    
    public int colorize(WeatherItem weatherItem)
    {
        int colorCode;
        
        switch (weatherItem)
        {
            case APPARENT_TEMPERATURE:
                colorCode = new TemperatureColorizer(clientRaw.getApparentTemperature()).colorize();
                break;
            
            case DEW_POINT:
                colorCode = new TemperatureColorizer(clientRaw.getDewPoint()).colorize();
                break;
                
            case HEAT_INDEX:
                colorCode = new TemperatureColorizer(clientRaw.getHeatIndex()).colorize();
                break;
                
            case HUMIDEX:
                colorCode = new TemperatureColorizer(clientRaw.getHumidex()).colorize();
                break;
                
            case UV_INDEX:
                colorCode = new UvIndexColorizer(clientRaw.getUvIndex()).colorize();
                break;
                
            case WIND_CHILL: 
                colorCode = new TemperatureColorizer(clientRaw.getWindChill()).colorize();
                break;
                
            default:
                colorCode = WHITE;
        }
        
        return colorCode;
    }
}
