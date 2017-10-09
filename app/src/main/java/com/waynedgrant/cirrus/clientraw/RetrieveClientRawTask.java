/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.waynedgrant.cirrus.UpdateWidgetService;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class RetrieveClientRawTask extends AsyncTask<ClientRawRequest, Void, List<ClientRawResponse>>
{
    private static final String TAG = "RetrieveClientRawTask";

    private static final int MAX_FETCH_CLIENT_RAW_ATTEMPTS = 3;
    private static final long WAIT_BETWEEN_FETCH_CLIENT_RAW_ATTEMPTS_MSECS = 1000;
    private static final int MAX_IS_ONLINE_ATTEMPTS = 3;
    private static final long MAX_WAIT_BETWEEN_IS_ONLINE_ATTEMPTS_MSECS = 1000;


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
        Log.d(TAG, "doInBackground()");

        List<ClientRawResponse> responses = new ArrayList<ClientRawResponse>();

        for (ClientRawRequest request : requests)
        {
            int appWidgetId = request.getAppWidgetId();
            ClientRawUrl clientRawUrl = request.getClientRawUrl();

            ClientRawResponse response;

            if (!whileNotOnlineRetryCheck(isOnline()))
            {
                response = new ClientRawResponse(appWidgetId, "Not online");
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
            Log.d(TAG, String.format("Fetched clientraw.txt is empty, attempting retry %s of %d",
                    fetchClientRawAttempts, MAX_FETCH_CLIENT_RAW_ATTEMPTS));

            waitFor(WAIT_BETWEEN_FETCH_CLIENT_RAW_ATTEMPTS_MSECS);
            clientRaw = fetchClientRaw(clientRawUrl);
            fetchClientRawAttempts++;
        }

        return clientRaw;
    }

    private ClientRaw fetchClientRaw(ClientRawUrl clientRawUrl) throws IOException
    {
        Log.d(TAG, String.format("Fetching clientraw.txt from %s", clientRawUrl));

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
            String errorMessage = "Empty clientraw.txt received";
            Log.d(TAG, errorMessage);
            response = new ClientRawResponse(appWidgetId, errorMessage);
        }
        else if (!clientRaw.isValid())
        {
            String errorMessage = "Invalid clientraw.txt received";
            Log.d(TAG, errorMessage);
            response = new ClientRawResponse(appWidgetId, errorMessage);
        }
        else
        {
            Log.d(TAG, "Valid clientraw.txt received");
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
        Log.e(TAG, "Exception occurred when fetching clientraw.txt", exception);

        ClientRawResponse response;

        if (exception instanceof SocketTimeoutException)
        {
            response = new ClientRawResponse(appWidgetId, "Connection timed out");
        }
        else if (exception instanceof FileNotFoundException)
        {
            response = new ClientRawResponse(appWidgetId, "clientraw.txt not found");
        }
        else if (exception instanceof UnknownHostException)
        {
            response = new ClientRawResponse(appWidgetId, "Unknown host");
        }
        else
        {
            response = new ClientRawResponse(appWidgetId, "Connection issue");
        }

        return response;
    }

    private boolean whileNotOnlineRetryCheck(boolean isOnline)
    {
        int isOnlineAttempts = 1;

        while (!isOnline && isOnlineAttempts <= MAX_IS_ONLINE_ATTEMPTS)
        {
            Log.d(TAG, String.format("Not online, attempting retry of online check %s of %d",
                    isOnlineAttempts, MAX_IS_ONLINE_ATTEMPTS));

            waitFor(MAX_WAIT_BETWEEN_IS_ONLINE_ATTEMPTS_MSECS);
            isOnline = isOnline();
            isOnlineAttempts++;
        }

        return isOnline;
    }

    private boolean isOnline()
    {
        Log.d(TAG, "Checking if device is online");

        ConnectivityManager connectivityManager =
                (ConnectivityManager)updateWidgetService.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Log.d(TAG, "networkInfo=" + networkInfo);

        boolean isOnline = (networkInfo != null && networkInfo.isConnected());

        Log.d(TAG, String.format("Device is online=%b", isOnline));

        return isOnline;
    }

    protected void onPostExecute(List<ClientRawResponse> responses)
    {
        Log.d(TAG, "onPostExecute()");

        updateWidgetService.handleClientRawResponses(responses);
    }
}
