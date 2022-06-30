package com.orbi.orbimc.math;

import java.text.DecimalFormat;

public class Converter {

    static public String formatValue(float value, ConvertType convertType) {
        String arr[] = convertType.getChars();
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value), arr[index]);
    }

    static public String formatValue(float value) {
        String arr[] = ConvertType.DEFAULT.getChars();
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.## C");
        return String.format("%s %s", decimalFormat.format(value), arr[index]);
    }

    static public String formatValue(String value) {
        String arr[] = ConvertType.DEFAULT.getChars();
        float value2 = Float.parseFloat(value);
        int index = 0;
        while ((value2 / 1000) >= 1) {
            value2 = value2 / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value2), arr[index]);
    }

}
