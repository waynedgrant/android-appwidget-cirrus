/* Copyright 2014 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus;

import java.text.MessageFormat;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.waynedgrant.cirrus.preferences.Preferences;

public class WidgetProvider extends AppWidgetProvider
{
    public static final String ERROR_ACTION = "com.waynedgrant.cirrus.error";
    public static final String ERROR_MESSAGE_EXTRA = "com.waynedgrant.cirrus.errorMessage";
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        callUpdateService(context, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    private void callUpdateService(Context context, int[] appWidgetIds)
    {
        Intent intent = new Intent(context.getApplicationContext(), UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.startService(intent);
    }
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(ERROR_ACTION))
        {
            String errorMessage = intent.getStringExtra(ERROR_MESSAGE_EXTRA);
            
            if (errorMessage != null)
            {
                String toastErrorMessage =
                        MessageFormat.format(context.getString(R.string.cirrusError_message),
                                context.getString(R.string.app_name), errorMessage);
                Toast.makeText(context, toastErrorMessage, Toast.LENGTH_SHORT).show();
            }
        }

        super.onReceive(context, intent);
    }
    
    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        deletePreferences(context, appWidgetIds);
        
        super.onDeleted(context, appWidgetIds);
    }
    
    private void deletePreferences(Context context, int[] appWidgetIds)
    {
        Preferences preferences = new Preferences(context);
       
        for (int appWidgetId : appWidgetIds)
        {
            preferences.removePreferences(appWidgetId);
        }
       
        preferences.commit();
    }
}
