package com.sms.billing.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

public class DateUtils {

    public static LocalDateTime getStartOfCurrentMonth(){
        LocalDateTime now = LocalDateTime.now();
        YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonthValue());
        return LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN);
    }

    public static LocalDateTime getEndOfCurrentMonth(){
        LocalDateTime now = LocalDateTime.now();
        YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonthValue());
        return LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
    }
}
