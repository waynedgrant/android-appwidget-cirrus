/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.waynedgrant.cirrus.UpdateWidgetService;

public class RetrieveClientRawTask extends AsyncTask<ClientRawRequest, Void, List<ClientRawResponse>>
{
    private static final int MAX_FETCH_CLIENT_RAW_ATTEMPTS = 3;
    private static final long WAIT_BETWEEN_FETCH_CLIENT_RAW_ATTEMPTS_MSECS = 1000;

    private UpdateWidgetService updateWidgetService;
    private int connectTimeoutMs;
    private int readTimeoutMs;

    public RetrieveClientRawTask(UpdateWidgetService updateWidgetService, int connectTimeoutMs, int readTimeoutMs)
    {
        this.updateWidgetService = updateWidgetService;
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    protected List<ClientRawResponse> doInBackground(ClientRawRequest... requests)
    {
        List<ClientRawResponse> responses = new ArrayList<ClientRawResponse>();

        for (ClientRawRequest request : requests)
        {
            int appWidgetId = request.getAppWidgetId();
            ClientRawUrl clientRawUrl = request.getClientRawUrl();

            ClientRawResponse response;

            if (!isOnline())
            {
                response = new ClientRawResponse(appWidgetId, "not online");
            }
            else
            {
                try
                {
                    ClientRaw clientRaw = whileClientRawEmptyRetryFetch(clientRawUrl, fetchClientRaw(clientRawUrl));
                    response = handleClientRaw(appWidgetId, clientRaw);
                }
                catch (IOException ex)
                {
                    response = handleException(appWidgetId, ex);
                }
            }

            responses.add(response);
        }

        return responses;
    }

    private ClientRaw whileClientRawEmptyRetryFetch(ClientRawUrl clientRawUrl, ClientRaw clientRaw) throws IOException
    {
        /* clientraw.txt may have been being written to on the server-side when we read.
           This results in an empty file being returned in which case we try fetching again */
        int fetchClientRawAttempts = 1;

        while (clientRaw.isEmpty() && fetchClientRawAttempts <= MAX_FETCH_CLIENT_RAW_ATTEMPTS)
        {
            waitFor(WAIT_BETWEEN_FETCH_CLIENT_RAW_ATTEMPTS_MSECS);
            clientRaw = fetchClientRaw(clientRawUrl);
            fetchClientRawAttempts++;
        }

        return clientRaw;
    }

    private ClientRaw fetchClientRaw(ClientRawUrl clientRawUrl) throws IOException
    {
        ClientRaw clientRaw;
        URLConnection connection = new URL(clientRawUrl.getUrl()).openConnection();

        connection.setConnectTimeout(connectTimeoutMs);
        connection.setReadTimeout(readTimeoutMs);

        BufferedInputStream reader = null;

        try
        {
            reader = new BufferedInputStream(connection.getInputStream());
            clientRaw = ClientRaw.getInstance(reader);
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }
        return clientRaw;
    }

    private ClientRawResponse handleClientRaw(int appWidgetId, ClientRaw clientRaw) throws IOException
    {
        ClientRawResponse response;

        if (clientRaw.isEmpty())
        {
            response = new ClientRawResponse(appWidgetId, "empty clientraw.txt received");
        }
        else if (!clientRaw.isValid())
        {
            response = new ClientRawResponse(appWidgetId, "invalid clientraw.txt received");
        }
        else
        {
            response = new ClientRawResponse(appWidgetId, clientRaw);
        }

        return response;
    }

    private void waitFor(long waitTimeMsecs)
    {
        try
        {
            Thread.sleep(waitTimeMsecs);
        }
        catch (InterruptedException ex)
        {
            // Ignore
        }
    }

    private ClientRawResponse handleException(int appWidgetId, IOException exception)
    {
        ClientRawResponse response;

        if (exception instanceof SocketTimeoutException)
        {
            response = new ClientRawResponse(appWidgetId, "connection timed out");
        }
        else if (exception instanceof FileNotFoundException)
        {
            response = new ClientRawResponse(appWidgetId, "clientraw.txt not found");
        }
        else if (exception instanceof UnknownHostException)
        {
            response = new ClientRawResponse(appWidgetId, "unknown host");
        }
        else
        {
            response = new ClientRawResponse(appWidgetId, "connection issue");
        }

        return response;
    }

    private boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager)updateWidgetService.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    protected void onPostExecute(List<ClientRawResponse> responses)
    {
        updateWidgetService.handleClientRawResponses(responses);
    }
}
