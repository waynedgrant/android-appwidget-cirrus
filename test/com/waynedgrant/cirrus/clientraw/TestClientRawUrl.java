package com.waynedgrant.cirrus.clientraw;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class TestClientRawUrl
{
    private ClientRawUrl testee;
    
    @Test
    public void GIVEN_url_with_leading_protocol_AND_trailing_client_raw_txt_WHEN_url_requested_THEN_returns_unchanged_url()
    {
        testee = new ClientRawUrl("http://www.waynegrant.com/weather/clientraw.txt");
        assertEquals("http://www.waynegrant.com/weather/clientraw.txt", testee.getUrl());
    }
    
    @Test
    public void GIVEN_url_with_trailing_slash_WHEN_url_requested_THEN_returns_url_with_client_raw_txt_appended()
    {
        testee = new ClientRawUrl("http://www.waynegrant.com/weather/");
        assertEquals("http://www.waynegrant.com/weather/clientraw.txt", testee.getUrl());
    }
    
    @Test
    public void GIVEN_url_without_trailing_slash_WHEN_url_requested_THEN_returns_url_with_slash_and_client_raw_txt_appended()
    {
        testee = new ClientRawUrl("http://www.waynegrant.com/weather");
        assertEquals("http://www.waynegrant.com/weather/clientraw.txt", testee.getUrl());
    }
    
    @Test
    public void GIVEN_url_without_leading_protocol_WHEN_url_requested_THEN_returns_url_with_http_prepended()
    {
        testee = new ClientRawUrl("www.waynegrant.com/weather/clientraw.txt");
        assertEquals("http://www.waynegrant.com/weather/clientraw.txt", testee.getUrl());
    }
}
