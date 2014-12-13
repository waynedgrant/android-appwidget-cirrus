/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

import android.util.SparseArray;

public class ClientRawCache
{
    private static SparseArray<ClientRaw> cache = new SparseArray<ClientRaw>();
    
    private ClientRawCache() {}
    
    public static void update(int appWidgetId, ClientRaw clientRaw)
    {
        cache.put(appWidgetId, clientRaw);
    }
    
    public static ClientRaw fetch(int appWidgetId)
    {
        return cache.get(appWidgetId);
    }
    
    public static void remove(int appWidget)
    {
        cache.remove(appWidget);
    }
    
    public static boolean isCached(int appWidget)
    {
        return fetch(appWidget) != null;
    }
}
