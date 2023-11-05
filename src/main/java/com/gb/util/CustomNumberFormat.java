package com.gb.util;

import java.text.DecimalFormat;

public class CustomNumberFormat extends DecimalFormat {
    public CustomNumberFormat() {
        super("###,###");
    }

    public static String shortenValue(Number value) {
        double numericValue = value.doubleValue();

        if (numericValue >= 1e9) {
            return formatShortenedValue(numericValue / 1e9, "B");
        } else if (numericValue >= 1e6) {
            return formatShortenedValue(numericValue / 1e6, "M");
        } else if (numericValue >= 1e3) {
            return formatShortenedValue(numericValue / 1e3, "K");
        } else {
            return formatShortenedValue(numericValue, "");
        }
    }

    private static String formatShortenedValue(Number value, String suffix) {
        var formattedValue = String.format("%.3f", value.doubleValue());

        // Remove trailing zeros from the decimal portion
        formattedValue = formattedValue.replaceAll("\\.0*$", "");

        // If there are more than 1 decimal place remaining, remove the excess
        formattedValue = formattedValue.replaceAll("(\\.\\d*?)0+$", "$1");

        return formattedValue + suffix;
    }
}
