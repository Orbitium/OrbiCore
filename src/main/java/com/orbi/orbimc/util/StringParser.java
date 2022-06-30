package com.orbi.orbimc.util;

import com.orbi.orbimc.math.ConvertType;
import com.orbi.orbimc.math.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

    public static String parse(String string, Map dataMap) {
        List<String> pointList = new ArrayList<>();
        int start = -1;
        int location = 0;

        for (char c : string.toCharArray()) {
            if (c == '%' && start == -1) {
                start = location;
            } else if (c == '%') {
                pointList.add(string.substring(start, location + 1));
                start = -1;
            }
            location++;
        }
        for (String point : pointList) {
            string = string.replace(point + "", dataMap.get(point.substring(1, point.length() - 1)) + "");
        }
        return string;
    }

    public static List<String> parse(Map dataMap, String... strings) {
        List<String> returnList = new ArrayList<>();
        for (String string : strings) {
            List<String> pointList = new ArrayList<>();
            int start = -1;
            int location = 0;

            for (char c : string.toCharArray()) {
                if (c == '%' && start == -1) {
                    start = location;
                } else if (c == '%') {
                    pointList.add(string.substring(start, location + 1));
                    start = -1;
                }
                location++;
            }
            for (String point : pointList) {
                string = string.replace(point + "", dataMap.get(point.substring(1, point.length() - 1)) + "");
            }
            returnList.add(Color.translateHex(string));
        }
        return returnList;
    }
    public static List<String> parse(Map dataMap, int convertIndex, ConvertType convertType, String... strings) {
        List<String> returnList = new ArrayList<>();
        for (String string : strings) { //TODO -> BİR ARA BUNU OPTİMİZE ET MATCHER KISMINI FALAN
            List<String> pointList = new ArrayList<>();
            int start = -1;
            int location = 0;

            for (char c : string.toCharArray()) {
                if (c == '%' && start == -1) {
                    start = location;
                } else if (c == '%') {
                    pointList.add(string.substring(start, location + 1));
                    start = -1;
                }
                location++;
            }
            for (String point : pointList) {
                string = string.replace(point + "", dataMap.get(point.substring(1, point.length() - 1)) + "");
            }
            returnList.add(Color.translateHex(string));
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(returnList.get(convertIndex));
        String lastGroup = null;
        while (matcher.find()) {
            lastGroup = matcher.group();
        }
        returnList.set(convertIndex, returnList.get(convertIndex).replace(lastGroup, Converter.formatValue(Float.parseFloat(lastGroup), convertType)));
        return returnList;
    }

    public static List<String> parse(Map dataMap, List<String> strings) {
        List<String> returnList = new ArrayList<>();
        for (String string : strings) {
            List<String> pointList = new ArrayList<>();
            int start = -1;
            int location = 0;

            for (char c : string.toCharArray()) {
                if (c == '%' && start == -1) {
                    start = location;
                } else if (c == '%') {
                    pointList.add(string.substring(start, location + 1));
                    start = -1;
                }
                location++;
            }
            for (String point : pointList) {
                string = string.replace(point + "", dataMap.get(point.substring(1, point.length() - 1)) + "");
            }
            returnList.add(Color.translateHex(string));
        }
        return returnList;
    }


    public static String parse(String string, Object value) {
        int start = -1;
        int location = 0;
        for (char c : string.toCharArray()) {
            if (c == '%' && start == -1) {
                start = location;
            } else if (c == '%') {
                return string.replaceAll(string.substring(start, location + 1) + "", value + "");
            }
            location++;
        }
        throw new NullPointerException();
    }
}
