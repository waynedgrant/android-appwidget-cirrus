package com.waynedgrant.cirrus.measures;

import com.waynedgrant.cirrus.units.CardinalDirection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestWindDirection
{
    private WindDirection testee;
    
    @Test
    public void GIVEN_populated_with_compass_degrees_WHEN_degrees_requested_THEN_returns_correct_value()
    {
        testee = new WindDirection(180);
        assertEquals((Integer)180, testee.getCompassDegrees());
    }
    
    @Test
    public void GIVEN_populated_with_compass_degrees_WHEN_cardinal_direction_requested_THEN_returns_correct_value()
    {
        testee = new WindDirection(0);
        assertEquals(CardinalDirection.N, testee.getCardinalDirection());
        
        testee = new WindDirection(22);
        assertEquals(CardinalDirection.NNE, testee.getCardinalDirection());
        
        testee = new WindDirection(45);
        assertEquals(CardinalDirection.NE, testee.getCardinalDirection());
        
        testee = new WindDirection(68);
        assertEquals(CardinalDirection.ENE, testee.getCardinalDirection());
        
        testee = new WindDirection(90);
        assertEquals(CardinalDirection.E, testee.getCardinalDirection());
        
        testee = new WindDirection(112);
        assertEquals(CardinalDirection.ESE, testee.getCardinalDirection());
        
        testee = new WindDirection(135);
        assertEquals(CardinalDirection.SE, testee.getCardinalDirection());
        
        testee = new WindDirection(158);
        assertEquals(CardinalDirection.SSE, testee.getCardinalDirection());
        
        testee = new WindDirection(180);
        assertEquals(CardinalDirection.S, testee.getCardinalDirection());
        
        testee = new WindDirection(202);
        assertEquals(CardinalDirection.SSW, testee.getCardinalDirection());
        
        testee = new WindDirection(225);
        assertEquals(CardinalDirection.SW, testee.getCardinalDirection());
        
        testee = new WindDirection(248);
        assertEquals(CardinalDirection.WSW, testee.getCardinalDirection());
        
        testee = new WindDirection(270);
        assertEquals(CardinalDirection.W, testee.getCardinalDirection());
        
        testee = new WindDirection(292);
        assertEquals(CardinalDirection.WNW, testee.getCardinalDirection());
        
        testee = new WindDirection(315);
        assertEquals(CardinalDirection.NW, testee.getCardinalDirection());
        
        testee = new WindDirection(338);
        assertEquals(CardinalDirection.NNW, testee.getCardinalDirection());
    }
}
