/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

public enum Conditions {
    SUNNY_1(0, "Sunny"),
    CLEAR_NIGHT(1, "Clear Night"),
    CLOUDY_1(2, "Cloudy"),
    CLOUDY_2(3, "Cloudy"),
    CLOUDY_NIGHT(4, "Cloudy Night"),
    DRY_CLEAR(5, "Dry Clear"),
    FOG(6, "Fog"),
    HAZY(7, "Hazy"),
    HEAVY_RAIN(8, "Heavy Rain"),
    MAINLY_FINE(9, "Mainly Fine"),
    MISTY(10, "Misty"),
    NIGHT_FOG(11, "Night Fog"),
    NIGHT_HEAVY_RAIN(12, "Night Heavy Rain"),
    NIGHT_OVERCAST(13, "Night Overcast"),
    NIGHT_RAIN(14, "Night Rain"),
    NIGHT_SHOWERS(15, "Night Showers"),
    NIGHT_SNOW(16, "Night Snow"),
    NIGHT_THUNDER(17, "Night Thunder"),
    OVERCAST(18, "Overcast"),
    PARTLY_CLOUDY(19, "Partly Cloudy"),
    RAIN(20, "Rain"),
    HARD_RAIN(21, "Hard Rain"),
    SHOWERS(22, "Showers"),
    SLEET(23, "Sleet"),
    SLEET_SHOWERS(24, "Sleet Showers"),
    SNOWING(25, "Snow"),
    SNOW_MELT(26, "Snow Melt"),
    SNOW_SHOWERS(27, "Snow Showers"),
    SUNNY_2(28, "Sunny"),
    THUNDER_SHOWERS_1(29, "Thunder Showers"),
    THUNDER_SHOWERS_2(30, "Thunder Showers"),
    THUNDERSTORMS(31, "Thunderstorms"),
    TORNADO_WARNING(32, "Tornado Warning"),
    WINDY(33, "Windy"),
    STOPPED_RAINING(34, "Stopped Raining"),
    WINDY_RAIN(35, "Windy Rain");

    private int icon;
    private String description;

    Conditions(int icon, String description) {
        this.icon = icon;
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public static Conditions resolveIcon(int icon) {
        Conditions resolved = null;

        for (Conditions conditions : Conditions.values()) {
            if (conditions.icon == icon) {
                resolved = conditions;
                break;
            }
        }

        return resolved;
    }
}
