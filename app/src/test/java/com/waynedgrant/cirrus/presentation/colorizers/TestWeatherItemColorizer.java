package com.waynedgrant.cirrus.presentation.colorizers;

import com.waynedgrant.cirrus.ClientRawGenerator;
import com.waynedgrant.cirrus.clientraw.ClientRaw;
import com.waynedgrant.cirrus.measures.WeatherItem;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class TestWeatherItemColorizer
{
    private WeatherItemColorizer testee;
    
    @Before
    public void setUp() throws IOException
    {
        ClientRaw clientRaw = new ClientRawGenerator().generatedPopulated();
        
        testee = new WeatherItemColorizer(clientRaw);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_apparent_temperature_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.APPARENT_TEMPERATURE);
        assertEquals(TemperatureColorizer.LIGHT_BLUE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_average_wind_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.AVERAGE_WIND);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_blank_color_requested_THEN_blank_value_returned()
    {
        int color = testee.colorize(WeatherItem.BLANK);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_daily_rainfall_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.DAILY_RAINFALL);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_dew_point_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.DEW_POINT);
        assertEquals(TemperatureColorizer.DARK_YELLOW, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_forecast_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.FORECAST);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_gust_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.GUST);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_heat_index_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.HEAT_INDEX);
        assertEquals(TemperatureColorizer.LIGHT_ORANGE, color);
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_humidex_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.HUMIDEX);
        assertEquals(TemperatureColorizer.MEDIUM_ORANGE, color);
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_humidity_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.HUMIDITY);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_indoor_conditions_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.INDOOR_CONDITIONS);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_rain_rate_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.RAINFALL_RATE);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_solar_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.SOLAR);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_surface_pressure_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.SURFACE_PRESSURE);
        assertEquals(WeatherItemColorizer.WHITE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_uv_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.UV_INDEX);
        assertEquals(UvIndexColorizer.ORANGE, color);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_wind_chill_color_requested_THEN_color_returned()
    {
        int color = testee.colorize(WeatherItem.WIND_CHILL);
        assertEquals(TemperatureColorizer.DARK_ORANGE, color);
    }
}
