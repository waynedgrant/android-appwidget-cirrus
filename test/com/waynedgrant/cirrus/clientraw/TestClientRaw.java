package com.waynedgrant.cirrus.clientraw;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.waynedgrant.cirrus.BigDecimalEquals;
import com.waynedgrant.cirrus.measures.Conditions;
import com.waynedgrant.cirrus.measures.Trend;
import com.waynedgrant.cirrus.units.PressureUnit;
import com.waynedgrant.cirrus.units.RainfallUnit;
import com.waynedgrant.cirrus.units.TemperatureUnit;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

public class TestClientRaw
{
    private ClientRaw testee;

    //
    // Emptiness
    //
    
    @Test
    public void GIVEN_empty_client_raw_WHEN_client_raw_emptiness_checked_THEN_returns_empty() throws IOException
    {
        testee = ClientRaw.getInstance(createEmptyClientRaw());
        assertTrue(testee.isEmpty());
    }
    
    @Test
    public void GIVEN_non_empty_client_raw_WHEN_client_raw_emptiness_checked_THEN_returns_not_empty() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HEADER, ClientRaw.VALID_HEADER_VALUE)));
        assertFalse(testee.isEmpty());
    }
    
    //
    // Validation
    //
    
    @Test
    public void GIVEN_header_field_populated_correctly_WHEN_client_raw_validated_THEN_validation_succeeds() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HEADER, ClientRaw.VALID_HEADER_VALUE)));
        assertTrue(testee.isValid());
    }
    
    @Test
    public void GIVEN_no_field_for_header_WHEN_client_raw_validated_THEN_validation_fails() throws IOException
    {
        testee = ClientRaw.getInstance(createEmptyClientRaw());
        assertFalse(testee.isValid());
    }
    
    @Test
    public void GIVEN_header_field_populated_incorrectly_WHEN_client_raw_validated_THEN_validation_succeeds() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HEADER, "54321")));
        assertFalse(testee.isValid());
    }
    
    //
    // Header
    //
    
    @Test
    public void GIVEN_header_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HEADER, "12345")));
        assertEquals("12345", testee.getHeader());
    }
    
    @Test
    public void GIVEN_no_field_for_header_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createEmptyClientRaw());
        assertNull(testee.getHeader());
    }
    
    //
    // Average Wind Speed
    //
    
    @Test
    public void GIVEN_average_wind_speed_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.AVERAGE_WIND_SPEED_KNOTS, "4.3"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("4.3"), testee.getAverageWindSpeed().getValue(WindSpeedUnit.KNOTS));
    }
    
    @Test
    public void GIVEN_average_wind_speed_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.AVERAGE_WIND_SPEED_KNOTS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getAverageWindSpeed());
    }
    
    @Test
    public void GIVEN_no_field_for_average_wind_speed_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(1));
        assertNull(testee.getAverageWindSpeed());
    }
    
    //
    // Gust Speed
    //
    
    @Test
    public void GIVEN_gust_speed_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.GUST_SPEED_KNOTS, "5.8"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("5.8"), testee.getGustSpeed().getValue(WindSpeedUnit.KNOTS));
    }
    
    @Test
    public void GIVEN_gust_speed_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.GUST_SPEED_KNOTS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getGustSpeed());
    }
    
    @Test
    public void GIVEN_no_field_for_gust_speed_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.GUST_SPEED_KNOTS));
        assertNull(testee.getGustSpeed());
    }
    
    //
    // Wind Direction
    //
    
    @Test
    public void GIVEN_wind_direction_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.WIND_DIRECTION_COMPASS_DEGREES, "128"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)128, testee.getWindDirection().getCompassDegrees());
    }
    
    @Test
    public void GIVEN_wind_direction_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.WIND_DIRECTION_COMPASS_DEGREES, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getWindDirection());
    }
    
    @Test
    public void GIVEN_no_field_for_wind_direction_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.WIND_DIRECTION_COMPASS_DEGREES));
        assertNull(testee.getWindDirection());
    }    
    
    //
    // Outdoor Temperature
    //
    
    @Test
    public void GIVEN_outdoor_temperature_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_TEMPERATURE_CELSIUS, "-15.5"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("-15.5"), testee.getOutdoorTemperature().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_outdoor_temperature_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_TEMPERATURE_CELSIUS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getOutdoorTemperature());
    }
    
    @Test
    public void GIVEN_no_field_for_outdoor_temperature_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.OUTDOOR_TEMPERATURE_CELSIUS));
        assertNull(testee.getOutdoorTemperature());
    }
    
    //
    // Outdoor Humidity
    //
    
    @Test
    public void GIVEN_outdoor_humidity_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_HUMIDITY_PERCENTAGE, "79"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)79, testee.getOutdoorHumidityPercentage());
    }
    
    @Test
    public void GIVEN_outdoor_humidity_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_HUMIDITY_PERCENTAGE, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getOutdoorHumidityPercentage());
    }
    
    @Test
    public void GIVEN_no_field_for_outdoor_humidity_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.OUTDOOR_HUMIDITY_PERCENTAGE));
        assertNull(testee.getOutdoorHumidityPercentage());
    }
    
    //
    // Surface Pressure
    //
    
    @Test
    public void GIVEN_surface_pressure_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SURFACE_PRESSURE_HECTOPASCALS, "1021.7"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("1021.7"), testee.getSurfacePressure().getValue(PressureUnit.HECTOPASCALS));
    }
    
    @Test
    public void GIVEN_surface_pressure_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SURFACE_PRESSURE_HECTOPASCALS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getSurfacePressure());
    }
    
    @Test
    public void GIVEN_no_field_for_surface_pressure_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.SURFACE_PRESSURE_HECTOPASCALS));
        assertNull(testee.getSurfacePressure());
    }
    
    //
    // Daily Rain
    //
    
    @Test
    public void GIVEN_daily_rainfall_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAILY_RAINFALL_MILLIMETRES, "10.21"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("10.21"), testee.getDailyRainfall().getValue(RainfallUnit.MILLIMETRES));
    }
    
    @Test
    public void GIVEN_daily_rainfall_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAILY_RAINFALL_MILLIMETRES, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getDailyRainfall());
    }
    
    @Test
    public void GIVEN_no_field_for_daily_rainfall_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.DAILY_RAINFALL_MILLIMETRES));
        assertNull(testee.getDailyRainfall());
    }    
    
    //
    // Rain Rate
    //
    
    @Test
    public void GIVEN_rainfall_rate_per_minute_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.RAINFALL_RATE_MILLIMETRES_PER_MINUTE, "0.12"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("0.12"), testee.getRainfallRatePerMinute().getValue(RainfallUnit.MILLIMETRES));
    }
    
    @Test
    public void GIVEN_rainfall_rate_per_minute_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.RAINFALL_RATE_MILLIMETRES_PER_MINUTE, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getRainfallRatePerMinute());
    }
    
    @Test
    public void GIVEN_no_field_for_rainfall_rate_per_minute_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.RAINFALL_RATE_MILLIMETRES_PER_MINUTE));
        assertNull(testee.getRainfallRatePerMinute());
    }    
    
    //
    // Indoor Temperature
    //
    
    @Test
    public void GIVEN_indoor_temperature_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.INDOOR_TEMPERATURE_CELSIUS, "18.9"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("18.9"), testee.getIndoorTemperature().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_indoor_temperature_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.INDOOR_TEMPERATURE_CELSIUS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getIndoorTemperature());
    }
    
    @Test
    public void GIVEN_no_field_for_indoor_temperature_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.INDOOR_TEMPERATURE_CELSIUS));
        assertNull(testee.getIndoorTemperature());
    }
    
    //
    // Indoor Humidity
    //
    
    @Test
    public void GIVEN_indoor_humidity_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.INDOOR_HUMIDITY_PERCENTAGE, "45"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)45, testee.getIndoorHumidityPercentage());
    }
    
    @Test
    public void GIVEN_indoor_humidity_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.INDOOR_HUMIDITY_PERCENTAGE, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getIndoorHumidityPercentage());
    }
    
    @Test
    public void GIVEN_no_field_for_indoor_humidity_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.INDOOR_HUMIDITY_PERCENTAGE));
        assertNull(testee.getIndoorHumidityPercentage());
    }
    
    //
    // Forecast
    //
    
    @Test
    public void GIVEN_forecast_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.FORECAST, "1"));      
        testee = ClientRaw.getInstance(is);
        assertEquals(Conditions.CLEAR_NIGHT, testee.getForecast());
    }
    
    @Test
    public void GIVEN_forecast_field_invalid_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.FORECAST, "666"));       
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getForecast());
    }
    
    @Test
    public void GIVEN_forecast_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.FORECAST, "-"));       
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getForecast());
    }
    
    @Test
    public void GIVEN_no_field_for_forecast_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.FORECAST));
        assertNull(testee.getForecast());
    }   
    
    //
    // Hour
    //
    
    @Test
    public void GIVEN_hour_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HOUR, "23"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)23, testee.getHour());
    }
    
    @Test
    public void GIVEN_hour_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HOUR, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getHour());
    }
    
    @Test
    public void GIVEN_no_field_for_hour_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.HOUR));
        assertNull(testee.getHour());
    }
    
    //
    // Minute
    //
    
    @Test
    public void GIVEN_minute_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.MINUTE, "59"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)59, testee.getMinute());
    }
    
    @Test
    public void GIVEN_minute_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.MINUTE, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getMinute());
    }
    
    @Test
    public void GIVEN_no_field_for_minute_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.MINUTE));
        assertNull(testee.getMinute());
    }        
    
    //
    // Seconds
    //
    
    @Test
    public void GIVEN_seconds_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SECONDS, "35"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)35, testee.getSeconds());
    }
    
    @Test
    public void GIVEN_seconds_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SECONDS, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getSeconds());
    }
    
    @Test
    public void GIVEN_no_field_for_seconds_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.SECONDS));
        assertNull(testee.getSeconds());
    }    
    
    //
    // Station Name
    //
    
    @Test
    public void GIVEN_station_name_field_populated_WHEN_accessed_THEN_returns_value_with_spaces_for_underscores_and_omits_trailing_time() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.STATION_NAME, "Clifton,_NJ,_USA-15:44:32"));
        testee = ClientRaw.getInstance(is);
        assertEquals("Clifton, NJ, USA", testee.getStationName());
    }
    
    @Test
    public void GIVEN_station_name_field_populated_and_with_surrounding_underscores_WHEN_accessed_THEN_returns_trimmed_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.STATION_NAME, "___Clifton,_NJ,_USA-15:44:32___"));
        testee = ClientRaw.getInstance(is);
        assertEquals("Clifton, NJ, USA", testee.getStationName());
    }
    
    @Test
    public void GIVEN_station_name_field_populated_and_with_multiple_internal_underscores_WHEN_accessed_THEN_returns_value_with_internal_spacing_minimized() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.STATION_NAME, "Clifton,___NJ,___USA-15:44:32"));
        testee = ClientRaw.getInstance(is);
        assertEquals("Clifton, NJ, USA", testee.getStationName());
    }
    
    @Test
    public void GIVEN_station_name_field_populated_with_time_only_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.STATION_NAME, "-15:44:32"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getStationName());
    }
    
    @Test
    public void GIVEN_station_name_field_populated_with_underscores_only_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.STATION_NAME, "_____"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getStationName());
    }
    
    @Test
    public void GIVEN_station_name_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.STATION_NAME, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getStationName());
    }
    
    @Test
    public void GIVEN_no_field_for_station_name_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.STATION_NAME));
        assertNull(testee.getStationName());
    }
    
    //
    // Solar Percentage
    //
    
    @Test
    public void GIVEN_solar_percentage_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SOLAR_PERCENTAGE, "79"));     
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)79, testee.getSolarPercentage());
    }
    
    @Test
    public void GIVEN_solar_percentage_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SOLAR_PERCENTAGE, "-"));      
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getSolarPercentage());
    }
    
    @Test
    public void GIVEN_no_field_for_solar_percentage_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.SOLAR_PERCENTAGE));
        assertNull(testee.getSolarPercentage());
    }
    
    //
    // Day
    //
    
    @Test
    public void GIVEN_day_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAY, "31"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)31, testee.getDay());
    }
    
    @Test
    public void GIVEN_day_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAY, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getDay());
    }
    
    @Test
    public void GIVEN_no_field_for_day_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.DAY));
        assertNull(testee.getDay());
    }    
    
    //
    // Month
    //
    
    @Test
    public void GIVEN_month_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.MONTH, "12"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)12, testee.getMonth());
    }
    
    @Test
    public void GIVEN_month_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.MONTH, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getMonth());
    }
    
    @Test
    public void GIVEN_no_field_for_month_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.MONTH));
        assertNull(testee.getMonth());
    }    
    
    //
    // Wind Chill
    //
    
    @Test
    public void GIVEN_wind_chill_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.WIND_CHILL_CELSIUS, "-3.4"));        
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("-3.4"), testee.getWindChill().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_wind_chill_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.WIND_CHILL_CELSIUS, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getWindChill());
    }
    
    @Test
    public void GIVEN_no_field_for_wind_chill_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.WIND_CHILL_CELSIUS));
        assertNull(testee.getWindChill());
    }
    
    //
    // Humidex
    //
    
    @Test
    public void GIVEN_humidex_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HUMIDEX_CELSIUS, "15.5"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("15.5"), testee.getHumidex().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_humidex_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HUMIDEX_CELSIUS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getHumidex());
    }
    
    @Test
    public void GIVEN_no_field_for_humidex_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.HUMIDEX_CELSIUS));
        assertNull(testee.getHumidex());
    }
    
    //
    // Daily Max Outdoor Temperature
    //
    
    @Test
    public void GIVEN_daily_max_outdoor_temperature_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAILY_MAX_OUTDOOR_TEMPERATURE_CELSIUS, "10.5"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("10.5"), testee.getDailyMaxOutdoorTemperature().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_daily_max_outdoor_temperature_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAILY_MAX_OUTDOOR_TEMPERATURE_CELSIUS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getDailyMaxOutdoorTemperature());
    }
    
    @Test
    public void GIVEN_no_field_for_daily_max_outdoor_temperature_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.DAILY_MAX_OUTDOOR_TEMPERATURE_CELSIUS));
        assertNull(testee.getDailyMaxOutdoorTemperature());
    }
    
    //
    // Daily Min Outdoor Temperature
    //
    
    @Test
    public void GIVEN_daily_min_outdoor_temperature_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAILY_MIN_OUTDOOR_TEMPERATURE_CELSIUS, "-5.6"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("-5.6"), testee.getDailyMinOutdoorTemperature().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_daily_min_outdoor_temperature_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DAILY_MIN_OUTDOOR_TEMPERATURE_CELSIUS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getDailyMinOutdoorTemperature());
    }
    
    @Test
    public void GIVEN_no_field_for_daily_min_outdoor_temperature_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.DAILY_MIN_OUTDOOR_TEMPERATURE_CELSIUS));
        assertNull(testee.getDailyMinOutdoorTemperature());
    }    
    
    //
    // Current Conditions
    //
    
    @Test
    public void GIVEN_current_conditions_field_populated_WHEN_accessed_THEN_returns_value_with_spaces_for_underscores() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.CURRENT_CONDITIONS_DESCRIPTION, "Dry/Night_time"));
        testee = ClientRaw.getInstance(is);
        assertEquals("Dry/Night time", testee.getCurrentConditionsDescription());
    }
    
    @Test
    public void GIVEN_current_conditions_field_populated_and_with_surrounding_underscores_WHEN_accessed_THEN_returns_trimmed_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.CURRENT_CONDITIONS_DESCRIPTION, "___Dry/Night_time___"));
        testee = ClientRaw.getInstance(is);
        assertEquals("Dry/Night time", testee.getCurrentConditionsDescription());
    }
    
    @Test
    public void GIVEN_current_conditions_field_populated_and_with_multiple_internal_underscores_WHEN_accessed_THEN_returns_value_with_internal_spacing_minimized() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.CURRENT_CONDITIONS_DESCRIPTION, "Dry/Night___time"));
        testee = ClientRaw.getInstance(is);
        assertEquals("Dry/Night time", testee.getCurrentConditionsDescription());
    }
    
    @Test
    public void GIVEN_current_conditions_field_populated_with_underscores_only_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.CURRENT_CONDITIONS_DESCRIPTION, "_____"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getCurrentConditionsDescription());
    }
    
    @Test
    public void GIVEN_current_conditions_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.CURRENT_CONDITIONS_DESCRIPTION, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getCurrentConditionsDescription());
    }
    
    @Test
    public void GIVEN_no_field_for_current_conditions_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.CURRENT_CONDITIONS_DESCRIPTION));
        assertNull(testee.getCurrentConditionsDescription());
    }    
    
    //
    // Surface Pressure Trend
    //
    
    @Test
    public void GIVEN_surface_pressure_trend_field_populated_rising_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SURFACE_PRESSURE_TREND, "1.5"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.RISING, testee.getSurfacePressureTrend());
    }
    
    @Test
    public void GIVEN_surface_pressure_trend_field_populated_falling_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SURFACE_PRESSURE_TREND, "-2.5"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.FALLING, testee.getSurfacePressureTrend());
    }    
    
    @Test
    public void GIVEN_surface_pressure_trend_field_populated_steady_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SURFACE_PRESSURE_TREND, "0"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.STEADY, testee.getSurfacePressureTrend());
    }
    
    @Test
    public void GIVEN_surface_pressure_trend_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SURFACE_PRESSURE_TREND, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getSurfacePressureTrend());
    }
    
    @Test
    public void GIVEN_no_field_for_surface_pressure_trend_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.SURFACE_PRESSURE_TREND));
        assertNull(testee.getSurfacePressureTrend());
    }
    
    //
    // Dew Point
    //
    
    @Test
    public void GIVEN_dew_point_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DEW_POINT_CELSIUS, "-5.1"));        
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("-5.1"), testee.getDewPoint().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_dew_point_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.DEW_POINT_CELSIUS, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getDewPoint());
    }
    
    @Test
    public void GIVEN_no_field_for_dew_point_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.DEW_POINT_CELSIUS));
        assertNull(testee.getDewPoint());
    }
    
    //
    // UV Index
    //
    
    @Test
    public void GIVEN_uv_index_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.UV_INDEX, "5.1"));      
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("5.1"), testee.getUvIndex());
    }
    
    @Test
    public void GIVEN_uv_index_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.UV_INDEX, "-"));     
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getUvIndex());
    }
    
    @Test
    public void GIVEN_no_field_for_uv_index_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.UV_INDEX));
        assertNull(testee.getUvIndex());
    }
    
    //
    // Heat Index
    //
    
    @Test
    public void GIVEN_heat_index_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HEAT_INDEX_CELSIUS, "15.5"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("15.5"), testee.getHeatIndex().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_heat_index_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.HEAT_INDEX_CELSIUS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getHeatIndex());
    }
    
    @Test
    public void GIVEN_no_field_for_heat_index_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.HEAT_INDEX_CELSIUS));
        assertNull(testee.getHeatIndex());
    }
    
    //
    // Solar Radiation
    //
    
    @Test
    public void GIVEN_solar_radiation_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SOLAR_RADIATION_WATTS_PER_METRE_SQUARED, "1342.5"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("1342.5"), testee.getSolarRadiation());
    }
    
    @Test
    public void GIVEN_solar_radiation_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.SOLAR_RADIATION_WATTS_PER_METRE_SQUARED, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getSolarRadiation());
    }
    
    @Test
    public void GIVEN_no_field_for_solar_radiation_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.SOLAR_RADIATION_WATTS_PER_METRE_SQUARED));
        assertNull(testee.getSolarRadiation());
    }
    
    //
    // Apparent Temperature
    //
    
    @Test
    public void GIVEN_apparent_temperature_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.APPARENT_TEMPERATURE_CELSUIS, "10.7"));
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("10.7"), testee.getApparentTemperature().getValue(TemperatureUnit.CELSIUS));
    }
    
    @Test
    public void GIVEN_apparent_temperature_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.APPARENT_TEMPERATURE_CELSUIS, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getApparentTemperature());
    }
    
    @Test
    public void GIVEN_no_field_for_apparent_temperature_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.APPARENT_TEMPERATURE_CELSUIS));
        assertNull(testee.getApparentTemperature());
    }
    
    //
    // Year
    //
    
    @Test
    public void GIVEN_year_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.YEAR, "2013"));        
        testee = ClientRaw.getInstance(is);
        assertEquals((Integer)2013, testee.getYear());
    }
    
    @Test
    public void GIVEN_year_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.YEAR, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getYear());
    }
    
    @Test
    public void GIVEN_no_field_for_year_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.YEAR));
        assertNull(testee.getYear());
    }    
    
    //
    // Outdoor Temperature Trend
    //
    
    @Test
    public void GIVEN_outdoor_temperature_trend_field_populated_rising_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_TEMPERATURE_TREND, "1"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.RISING, testee.getOutdoorTemperatureTrend());
    }
    
    @Test
    public void GIVEN_outdoor_temperature_trend_field_populated_falling_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_TEMPERATURE_TREND, "-1"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.FALLING, testee.getOutdoorTemperatureTrend());
    }    
    
    @Test
    public void GIVEN_outdoor_temperature_trend_field_populated_steady_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_TEMPERATURE_TREND, "0"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.STEADY, testee.getOutdoorTemperatureTrend());
    }        
    
    @Test
    public void GIVEN_outdoor_temperature_trend_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_TEMPERATURE_TREND, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getOutdoorTemperatureTrend());
    }
    
    @Test
    public void GIVEN_no_field_for_outdoor_temperature_trend_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.OUTDOOR_TEMPERATURE_TREND));
        assertNull(testee.getOutdoorTemperatureTrend());
    }    
    
    //
    // Outdoor Humidity Trend
    //
    
    @Test
    public void GIVEN_outdoor_humidity_trend_field_populated_rising_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_HUMIDITY_TREND, "1"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.RISING, testee.getOutdoorHumidityTrend());
    }
    
    @Test
    public void GIVEN_outdoor_humidity_trend_field_populated_falling_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_HUMIDITY_TREND, "-1"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.FALLING, testee.getOutdoorHumidityTrend());
    }    
    
    @Test
    public void GIVEN_outdoor_humidity_trend_field_populated_steady_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_HUMIDITY_TREND, "0"));
        testee = ClientRaw.getInstance(is);
        assertEquals(Trend.STEADY, testee.getOutdoorHumidityTrend());
    }
    
    @Test
    public void GIVEN_outdoor_humidity_trend_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.OUTDOOR_HUMIDITY_TREND, "-"));
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getOutdoorHumidityTrend());
    }
    
    @Test
    public void GIVEN_no_field_for_outdoor_humidity_trend_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.OUTDOOR_HUMIDITY_TREND));
        assertNull(testee.getOutdoorHumidityTrend());
    }
    
    //
    // Latitude
    //
    
    @Test
    public void GIVEN_latitude_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.LATITUDE_DECIMAL_DEGREES, "33.40250"));        
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("33.40250"), testee.getLatitudeDecimalDegrees());
    }
    
    @Test
    public void GIVEN_latitude_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.LATITUDE_DECIMAL_DEGREES, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getLatitudeDecimalDegrees());
    }
    
    @Test
    public void GIVEN_no_field_for_latitude_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.LATITUDE_DECIMAL_DEGREES));
        assertNull(testee.getLatitudeDecimalDegrees());
    }        
    
    //
    // Longitude
    //
    
    @Test
    public void GIVEN_longitude_field_populated_WHEN_accessed_THEN_returns_correct_value() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.LONGITUDE_DECIMAL_DEGREES, "-111.88306"));        
        testee = ClientRaw.getInstance(is);
        new BigDecimalEquals().assertEquals(new BigDecimal("-111.88306"), testee.getLongitudeDecimalDegrees());
    }
    
    @Test
    public void GIVEN_longitude_field_empty_WHEN_accessed_THEN_returns_null() throws IOException
    {
        InputStream is = createClientRawWithFields(new ClientRawStringGenerator.Field(ClientRaw.LONGITUDE_DECIMAL_DEGREES, "-"));        
        testee = ClientRaw.getInstance(is);
        assertNull(testee.getLongitudeDecimalDegrees());
    }
    
    @Test
    public void GIVEN_no_field_for_longitude_WHEN_accessed_THEN_returns_null() throws IOException
    {
        testee = ClientRaw.getInstance(createClientRawOfSize(ClientRaw.LONGITUDE_DECIMAL_DEGREES));
        assertNull(testee.getLongitudeDecimalDegrees());
    }        
    
    private InputStream createClientRawWithFields(ClientRawStringGenerator.Field... fields)
    {
        String clientRawText = new ClientRawStringGenerator().create(fields);
        return new ByteArrayInputStream(clientRawText.getBytes());
    }
    
    private InputStream createClientRawOfSize(int fields)
    {
        return createClientRawWithFields(new ClientRawStringGenerator.Field(fields-1, "-"));
    }    
    
    private InputStream createEmptyClientRaw()
    {
        return createClientRawWithFields();
    }
}
