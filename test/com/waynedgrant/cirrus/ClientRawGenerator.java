package com.waynedgrant.cirrus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.waynedgrant.cirrus.clientraw.ClientRaw;
import com.waynedgrant.cirrus.clientraw.ClientRawStringGenerator;
import com.waynedgrant.cirrus.clientraw.ClientRawStringGenerator.Field;
import com.waynedgrant.cirrus.measures.Conditions;

public class ClientRawGenerator
{
    public ClientRaw generatedPopulated() throws IOException
    {
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field(ClientRaw.APPARENT_TEMPERATURE_CELSUIS, "-15.6"));
        fields.add(new Field(ClientRaw.AVERAGE_WIND_SPEED_KNOTS, "10.5"));
        fields.add(new Field(ClientRaw.WIND_DIRECTION_COMPASS_DEGREES, "180"));
        fields.add(new Field(ClientRaw.DAILY_RAINFALL_MILLIMETRES, "5.71"));
        fields.add(new Field(ClientRaw.DEW_POINT_CELSIUS, "16.3"));
        fields.add(new Field(ClientRaw.FORECAST, ""+Conditions.SUNNY_1.getIcon()));
        fields.add(new Field(ClientRaw.GUST_SPEED_KNOTS, "7.8"));
        fields.add(new Field(ClientRaw.HEAT_INDEX_CELSIUS, "19.4"));
        fields.add(new Field(ClientRaw.HUMIDEX_CELSIUS, "23.7"));
        fields.add(new Field(ClientRaw.OUTDOOR_HUMIDITY_PERCENTAGE, "33"));
        fields.add(new Field(ClientRaw.OUTDOOR_HUMIDITY_TREND, "1"));
        fields.add(new Field(ClientRaw.INDOOR_TEMPERATURE_CELSIUS, "25.4"));
        fields.add(new Field(ClientRaw.INDOOR_HUMIDITY_PERCENTAGE, "29"));
        fields.add(new Field(ClientRaw.RAINFALL_RATE_MILLIMETRES_PER_MINUTE, "0.06"));
        fields.add(new Field(ClientRaw.SOLAR_RADIATION_WATTS_PER_METRE_SQUARED, "123.4"));
        fields.add(new Field(ClientRaw.SOLAR_PERCENTAGE, "56"));
        fields.add(new Field(ClientRaw.SURFACE_PRESSURE_HECTOPASCALS, "1024.5"));
        fields.add(new Field(ClientRaw.SURFACE_PRESSURE_TREND, "0"));
        fields.add(new Field(ClientRaw.UV_INDEX, "5.6"));
        fields.add(new Field(ClientRaw.WIND_CHILL_CELSIUS, "24.7"));
        
        return ClientRaw.getInstance(new ByteArrayInputStream(new ClientRawStringGenerator().create(fields.toArray(new Field[]{})).getBytes()));
    }
}
