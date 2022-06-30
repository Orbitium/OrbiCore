package com.orbi.orbimc.util;

import java.time.LocalDateTime;

public class TimeController {

    public static int dateToTimeValue() {
        int value = 0;
        LocalDateTime localDateTime = LocalDateTime.now();
        value += Integer.parseInt(String.valueOf(localDateTime.getYear()).substring(2, 4)) * 31556926;
        value += localDateTime.getDayOfYear() * 86400;
        value += localDateTime.getHour() * 3600;
        value += localDateTime.getMinute() * 60;
        value += localDateTime.getSecond();
        return value;
    }

    public static int calculateTimeSpacing(int oldValue) {
        return dateToTimeValue() - oldValue;
    }

    public static long calculateTimeSpacing(long oldValue) {
        return dateToTimeValue() - oldValue;
    }
}
