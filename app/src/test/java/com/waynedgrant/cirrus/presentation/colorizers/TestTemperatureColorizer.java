package com.waynedgrant.cirrus.presentation.colorizers;

import com.waynedgrant.cirrus.measures.Temperature;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;

public class TestTemperatureColorizer {
    private TemperatureColorizer testee;

    @Test
    public void GIVEN_null_temperature_WHEN_color_requested_THEN_white_color_returned() {
        testee = new TemperatureColorizer(null);
        assertEquals(TemperatureColorizer.WHITE, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_less_than_0_point_6_degrees_celsius_WHEN_color_requested_THEN_light_blue_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("0.5")));
        assertEquals(TemperatureColorizer.LIGHT_BLUE, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_0_point_6_and_3_point_5_degrees_celsius_WHEN_color_requested_THEN_dark_green_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("0.6")));
        assertEquals(TemperatureColorizer.DARK_GREEN, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("3.5")));
        assertEquals(TemperatureColorizer.DARK_GREEN, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_3_point_6_and_6_point_5_degrees_celsius_WHEN_color_requested_THEN_medium_green_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("3.6")));
        assertEquals(TemperatureColorizer.MEDIUM_GREEN, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("6.5")));
        assertEquals(TemperatureColorizer.MEDIUM_GREEN, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_6_point_6_and_9_point_5_degrees_celsius_WHEN_color_requested_THEN_light_green_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("6.6")));
        assertEquals(TemperatureColorizer.LIGHT_GREEN, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("9.5")));
        assertEquals(TemperatureColorizer.LIGHT_GREEN, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_9_point_6_and_12_point_5_degrees_celsius_WHEN_color_requested_THEN_light_yellow_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("9.6")));
        assertEquals(TemperatureColorizer.LIGHT_YELLOW, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("12.5")));
        assertEquals(TemperatureColorizer.LIGHT_YELLOW, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_12_point_6_and_15_point_5_degrees_celsius_WHEN_color_requested_THEN_medium_yellow_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("12.6")));
        assertEquals(TemperatureColorizer.MEDIUM_YELLOW, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("15.5")));
        assertEquals(TemperatureColorizer.MEDIUM_YELLOW, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_15_point_6_and_18_point_5_degrees_celsius_WHEN_color_requested_THEN_dark_yellow_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("15.6")));
        assertEquals(TemperatureColorizer.DARK_YELLOW, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("18.5")));
        assertEquals(TemperatureColorizer.DARK_YELLOW, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_18_point_6_and_21_point_5_degrees_celsius_WHEN_color_requested_THEN_light_orange_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("18.6")));
        assertEquals(TemperatureColorizer.LIGHT_ORANGE, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("21.5")));
        assertEquals(TemperatureColorizer.LIGHT_ORANGE, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_between_21_point_6_and_24_point_5_degrees_celsius_WHEN_color_requested_THEN_medium_orange_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("21.6")));
        assertEquals(TemperatureColorizer.MEDIUM_ORANGE, testee.colorize());

        testee = new TemperatureColorizer(new Temperature(new BigDecimal("24.5")));
        assertEquals(TemperatureColorizer.MEDIUM_ORANGE, testee.colorize());
    }

    @Test
    public void GIVEN_temperature_greater_than_24_point_5_degrees_celsius_WHEN_color_requested_THEN_dark_orange_color_returned() {
        testee = new TemperatureColorizer(new Temperature(new BigDecimal("24.6")));
        assertEquals(TemperatureColorizer.DARK_ORANGE, testee.colorize());
    }
}
