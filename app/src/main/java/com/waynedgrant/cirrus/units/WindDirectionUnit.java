/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.units;

public enum WindDirectionUnit {
    CARDINAL_DIRECTION("Cardinal Direction"),
    COMPASS_DEGREES("Compass Degrees");

    private String description;

    WindDirectionUnit(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
