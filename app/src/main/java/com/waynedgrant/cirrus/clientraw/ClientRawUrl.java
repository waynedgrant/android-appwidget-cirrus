/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.clientraw;

public class ClientRawUrl {
    private String url;

    public ClientRawUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        String correctedUrl = url;

        if (!url.endsWith("/clientraw.txt")) {
            if (!url.endsWith("/")) {
                correctedUrl += "/";
            }

            if (!url.endsWith("clientraw.txt")) {
                correctedUrl += "clientraw.txt";
            }
        }

        if (!url.contains("://")) {
            correctedUrl = "http://" + correctedUrl;
        }

        return correctedUrl;
    }

    @Override
    public String toString() {
        return "ClientRawUrl{" +
                "url='" + url + '\'' +
                '}';
    }
}
