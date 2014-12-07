package com.waynedgrant.cirrus.presentation.formatters;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.waynedgrant.cirrus.ClientRawGenerator;
import com.waynedgrant.cirrus.clientraw.ClientRaw;
import com.waynedgrant.cirrus.measures.WeatherItem;
import com.waynedgrant.cirrus.units.PressureUnit;
import com.waynedgrant.cirrus.units.RainfallUnit;
import com.waynedgrant.cirrus.units.TemperatureUnit;
import com.waynedgrant.cirrus.units.WindDirectionUnit;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

public class TestWeatherItemFormatter
{
    private WeatherItemFormatter testee;
    
    @Before
    public void setUp() throws IOException
    {
        ClientRaw clientRaw = new ClientRawGenerator().generatedPopulated();
        
        testee = new WeatherItemFormatter(
                clientRaw,
                TemperatureUnit.CELSIUS,
                PressureUnit.HECTOPASCALS,
                WindSpeedUnit.KNOTS,
                WindDirectionUnit.COMPASS_DEGREES,
                RainfallUnit.MILLIMETRES);
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_apparent_temperature_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.APPARENT_TEMPERATURE);
        assertEquals("apparent", formattedWeatherItem.getLabel());
        assertEquals("-15.6¡C", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_average_wind_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.AVERAGE_WIND);
        assertEquals("wind", formattedWeatherItem.getLabel());
        assertEquals("10.5 kts 180¡", formattedWeatherItem.getValue());
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_blank_formatting_requested_THEN_blank_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.BLANK);
        assertEquals("", formattedWeatherItem.getLabel());
        assertEquals("", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_daily_rainfall_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.DAILY_RAINFALL);
        assertEquals("daily rain", formattedWeatherItem.getLabel());
        assertEquals("5.71 mm", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_dew_point_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.DEW_POINT);
        assertEquals("dew point", formattedWeatherItem.getLabel());
        assertEquals("16.3¡C", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_forecast_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.FORECAST);
        assertEquals("fcst", formattedWeatherItem.getLabel());
        assertEquals("Sunny", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_gust_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.GUST);
        assertEquals("gust", formattedWeatherItem.getLabel());
        assertEquals("7.8 kts", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_heat_index_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.HEAT_INDEX);
        assertEquals("heat index", formattedWeatherItem.getLabel());
        assertEquals("19.4¡C", formattedWeatherItem.getValue());
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_humidex_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.HUMIDEX);
        assertEquals("humidex", formattedWeatherItem.getLabel());
        assertEquals("23.7¡C", formattedWeatherItem.getValue());
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_humidity_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.HUMIDITY);
        assertEquals("humidity", formattedWeatherItem.getLabel());
        assertEquals("33% \u279A", formattedWeatherItem.getValue());
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_indoor_conditions_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.INDOOR_CONDITIONS);
        assertEquals("indoor", formattedWeatherItem.getLabel());
        assertEquals("25.4¡C, 29%", formattedWeatherItem.getValue());
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_rain_rate_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.RAINFALL_RATE);
        assertEquals("rain rate", formattedWeatherItem.getLabel());
        assertEquals("0.06 mm/min", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_solar_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.SOLAR);
        assertEquals("solar", formattedWeatherItem.getLabel());
        assertEquals("123.4 W/m\u00b2, 56%", formattedWeatherItem.getValue());
    }

    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_surface_pressure_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.SURFACE_PRESSURE);
        assertEquals("press", formattedWeatherItem.getLabel());
        assertEquals("1024.5 hPa \u279E", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_uv_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.UV_INDEX);
        assertEquals("uv index", formattedWeatherItem.getLabel());
        assertEquals("5.6 high", formattedWeatherItem.getValue());
    }
    
    @Test
    public void GIVEN_populated_client_raw_fields_WHEN_wind_chill_formatting_requested_THEN_formatted_value_returned()
    {
        FormattedWeatherItem formattedWeatherItem = testee.format(WeatherItem.WIND_CHILL);
        assertEquals("wind chill", formattedWeatherItem.getLabel());
        assertEquals("24.7¡C", formattedWeatherItem.getValue());
    }
}
