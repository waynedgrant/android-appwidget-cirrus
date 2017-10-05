package com.waynedgrant.cirrus;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class BigDecimalEquals
{
    public void assertEquals(BigDecimal expected, BigDecimal actual)
    {
        assertTrue(expected.compareTo(actual) == 0);
    }
}
