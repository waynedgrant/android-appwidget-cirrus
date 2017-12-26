/* Copyright 2017 Wayne D Grant (www.waynedgrant.com)
   Licensed under the MIT License */

package com.waynedgrant.cirrus.presentation.formatters;

import com.waynedgrant.cirrus.measures.Conditions;

public class ConditionsFormatter {
    private Conditions conditions;

    public ConditionsFormatter(Conditions conditions) {
        this.conditions = conditions;
    }

    public String format() {
        String formatted = "-";

        if (conditions != null) {
            formatted = conditions.getDescription();
        }

        return formatted;
    }
}
