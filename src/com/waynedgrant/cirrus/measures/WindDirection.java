/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.measures;

import com.waynedgrant.cirrus.units.CardinalDirection;

public class WindDirection
{
    private Integer compassDegrees;
    private CardinalDirection cardinalDirection;
    
    public WindDirection(Integer degrees)
    {
        this.compassDegrees = degrees;
        this.cardinalDirection = CardinalDirection.values()[(int)(((degrees.intValue() + 11) / 22.5) % 16)];
    }
    
    public Integer getCompassDegrees()
    {
        return compassDegrees;
    }
    
    public CardinalDirection getCardinalDirection()
    {
        return cardinalDirection;
    }
}
