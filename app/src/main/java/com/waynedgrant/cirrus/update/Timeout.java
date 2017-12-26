/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.update;

public enum Timeout {
    NONE("None", 0),
    FIVE_SECONDS("5 Seconds", 5000),
    TEN_SECONDS("10 Seconds", 10000),
    THIRTY_SECONDS("30 Seconds", 30000),
    ONE_MINUTE("1 Minute", 60000);

    private String description;
    private int timeoutMsecs;

    Timeout(String description, int timeoutMsecs) {
        this.description = description;
        this.timeoutMsecs = timeoutMsecs;
    }

    public int getTimeoutMsecs() {
        return timeoutMsecs;
    }

    public String toString() {
        return description;
    }
}
