/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.clientraw.ClientRaw;
import com.waynedgrant.cirrus.measures.WeatherItem;
import com.waynedgrant.cirrus.units.PressureUnit;
import com.waynedgrant.cirrus.units.RainfallUnit;
import com.waynedgrant.cirrus.units.TemperatureUnit;
import com.waynedgrant.cirrus.units.WindDirectionUnit;
import com.waynedgrant.cirrus.units.WindSpeedUnit;

public class WeatherItemFormatter
{
    private ClientRaw clientRaw;
    private TemperatureUnit temperatureUnit;
    private PressureUnit pressureUnit;
    private WindSpeedUnit windSpeedUnit;
    private WindDirectionUnit windDirectionUnit;
    private RainfallUnit rainfallUnit;

    public WeatherItemFormatter(ClientRaw clientRaw, TemperatureUnit temperatureUnit, PressureUnit pressureUnit, WindSpeedUnit windSpeedUnit, WindDirectionUnit windDirectionUnit, RainfallUnit rainfallUnit)
    {
        this.clientRaw = clientRaw;
        this.temperatureUnit = temperatureUnit;
        this.pressureUnit = pressureUnit;
        this.windSpeedUnit = windSpeedUnit;
        this.windDirectionUnit = windDirectionUnit;
        this.rainfallUnit = rainfallUnit;
    }

    public FormattedWeatherItem format(WeatherItem weatherItem)
    {
        FormattedWeatherItem formattedWeatherItem = null;

        switch (weatherItem)
        {
            case APPARENT_TEMPERATURE:
                formattedWeatherItem = new FormattedWeatherItem("apparent",
                        new TemperatureFormatter(clientRaw.getApparentTemperature()).format(temperatureUnit));
                break;

            case AVERAGE_WIND:
                formattedWeatherItem = new FormattedWeatherItem("wind",
                        new WindSpeedFormatter(clientRaw.getAverageWindSpeed()).format(windSpeedUnit) + " " +
                        new WindDirectionFormatter(clientRaw.getWindDirection()).format(windDirectionUnit));
                break;

            case BLANK:
                formattedWeatherItem = new FormattedWeatherItem("", "");
                break;

            case DAILY_RAINFALL:
                formattedWeatherItem = new FormattedWeatherItem("daily rain",
                        new RainfallFormatter(clientRaw.getDailyRainfall()).format(rainfallUnit));
                break;

            case DEW_POINT:
                formattedWeatherItem = new FormattedWeatherItem("dew point",
                        new TemperatureFormatter(clientRaw.getDewPoint()).format(temperatureUnit));
                break;

            case FORECAST:
                formattedWeatherItem = new FormattedWeatherItem("fcst",
                        new ConditionsFormatter(clientRaw.getForecast()).format());
                break;

            case GUST:
                formattedWeatherItem = new FormattedWeatherItem("gust",
                        new WindSpeedFormatter(clientRaw.getGustSpeed()).format(windSpeedUnit));
                break;

            case HEAT_INDEX:
                formattedWeatherItem = new FormattedWeatherItem("heat index",
                        new TemperatureFormatter(clientRaw.getHeatIndex()).format(temperatureUnit));
                break;

            case HUMIDEX:
                formattedWeatherItem = new FormattedWeatherItem("humidex",
                        new TemperatureFormatter(clientRaw.getHumidex()).format(temperatureUnit));
                break;

            case HUMIDITY:
                formattedWeatherItem = new FormattedWeatherItem("humidity",
                        new HumidityFormatter(clientRaw.getOutdoorHumidityPercentage()).format() + " " +
                        new TrendFormatter(clientRaw.getOutdoorHumidityTrend()).format());
                break;

            case INDOOR_CONDITIONS:
                formattedWeatherItem = new FormattedWeatherItem("indoor",
                        new TemperatureFormatter(clientRaw.getIndoorTemperature()).format(temperatureUnit) + ", " +
                        new HumidityFormatter(clientRaw.getIndoorHumidityPercentage()).format());
                break;

            case RAINFALL_RATE:
                formattedWeatherItem = new FormattedWeatherItem("rain rate",
                        new RainfallRateFormatter(clientRaw.getRainfallRatePerMinute()).format(rainfallUnit));
                break;

            case SOLAR:
                formattedWeatherItem = new FormattedWeatherItem("solar",
                        new SolarRadiationFormatter(clientRaw.getSolarRadiation()).format() + ", " +
                        new SolarPercentageFormatter(clientRaw.getSolarPercentage()).format());
                break;

            case SURFACE_PRESSURE:
                formattedWeatherItem = new FormattedWeatherItem("press",
                        new PressureFormatter(clientRaw.getSurfacePressure()).format(pressureUnit) + " " +
                        new TrendFormatter(clientRaw.getSurfacePressureTrend()).format());
                break;

            case UV_INDEX:
                formattedWeatherItem = new FormattedWeatherItem("uv index",
                        new UvIndexFormatter(clientRaw.getUvIndex()).format());
                break;

            case WIND_CHILL:
                formattedWeatherItem = new FormattedWeatherItem("wind chill",
                        new TemperatureFormatter(clientRaw.getWindChill()).format(temperatureUnit));
                break;
        }

        return formattedWeatherItem;
    }
}
