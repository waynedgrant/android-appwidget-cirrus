/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

public class ClientRawResponse
{
    private int appWidgetId;
    private ClientRaw clientRaw;
    private String errorMessage;
    
    public ClientRawResponse(int appWidgetId, ClientRaw clientRaw)
    {
        this.appWidgetId = appWidgetId;
        this.clientRaw = clientRaw;
    }
    
    public ClientRawResponse(int appWidgetId, String errorMessage)
    {
        this.appWidgetId = appWidgetId;
        this.errorMessage = errorMessage;
    }

    public int getAppWidgetId()
    {
        return appWidgetId;
    }

    public ClientRaw getClientRaw()
    {
        return clientRaw;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
}
