/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.waynedgrant.cirrus.clientraw.ClientRawCache;
import com.waynedgrant.cirrus.preferences.Preferences;

import java.text.MessageFormat;

public class WidgetProvider extends AppWidgetProvider
{
    private static final String TAG = "WidgetProvider";

    public static final String ERROR_ACTION = "com.waynedgrant.cirrus.error";
    public static final String ERROR_MESSAGE_EXTRA = "com.waynedgrant.cirrus.errorMessage";
    public static final String FETCH_FRESH_CLIENT_RAW = "com.waynedgrant.cirrus.fetchFreshClientRaw";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        Log.d(TAG, "onUpdate()");

        callUpdateService(context, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void callUpdateService(Context context, int[] appWidgetIds)
    {
        Intent intent = new Intent(context.getApplicationContext(), UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        intent.putExtra(FETCH_FRESH_CLIENT_RAW, true);

        context.startService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "onReceive()");

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
        Log.d(TAG, "onDeleted()");

        deletePreferences(context, appWidgetIds);
        clearClientRawCache(appWidgetIds);
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

    private void clearClientRawCache(int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds)
        {
            ClientRawCache.remove(appWidgetId);
        }
    }
}
