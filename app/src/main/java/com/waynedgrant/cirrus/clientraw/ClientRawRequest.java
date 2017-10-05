/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

public class ClientRawRequest
{
    private int appWidgetId;
    private ClientRawUrl clientRawUrl;

    public ClientRawRequest(int appWidgetId, ClientRawUrl clientRawUrl)
    {
        this.appWidgetId = appWidgetId;
        this.clientRawUrl = clientRawUrl;
    }

    public int getAppWidgetId()
    {
        return appWidgetId;
    }

    public ClientRawUrl getClientRawUrl()
    {
        return clientRawUrl;
    }
}
