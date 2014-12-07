package com.waynedgrant.cirrus;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

public class BigDecimalEquals
{
    public void assertEquals(BigDecimal expected, BigDecimal actual)
    {
        assertTrue(expected.compareTo(actual) == 0);
    }
}
