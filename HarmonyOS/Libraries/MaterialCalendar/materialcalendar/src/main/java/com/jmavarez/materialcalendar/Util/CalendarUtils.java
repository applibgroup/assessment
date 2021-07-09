package com.jmavarez.materialcalendar.Util;

import ohos.global.resource.NotExistException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static Calendar getInstance(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        copyDateTo(calendar, calendar);
        return calendar;
    }

    public static Calendar getInstance() {
        Calendar calendar = Calendar.getInstance();
        copyDateTo(calendar, calendar);
        return calendar;
    }

    public static void setToFirstDay(Calendar calendar) {
        int year = getYear(calendar);
        int month = getMonth(calendar);
        calendar.clear();
        calendar.set(year, month, 1);
    }

    public static void copyDateTo(Calendar from, Calendar to) {
        int year = getYear(from);
        int month = getMonth(from);
        int day = getDay(from);
        to.clear();
        to.set(year, month, day);
    }

    public static int getDay(Calendar c) {
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfWeek(Calendar c, boolean startOnSunday) {
        return getConvertedDayOfWeek(c.get(Calendar.DAY_OF_WEEK), startOnSunday);
    }

    public static int getMonth(Calendar c) {
        return c.get(Calendar.MONTH);
    }

    public static int getYear(Calendar c) {
        return c.get(Calendar.YEAR);
    }

    public static int getEndOfMonth(Calendar c) {
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getConvertedDayOfWeek(int day, boolean startOnSunday) {
        if (startOnSunday) {
            return day;
        }
        return day == 1 ? 7 : day - 1;
    }

    public static class Day {
        private Integer mDay;

        public Day(@NotNull Integer day) throws Exception {
            this.mDay = null;
            if (day.intValue() < 1 || day.intValue() > 7) {
                throw new Exception("Day must be between 1 and 7");
            }
            this.mDay = day;
        }

        public Integer toInteger() {
            return this.mDay;
        }

        public String getShortName() throws IOException, NotExistException {
            String res_string;
            switch (this.mDay.intValue()) {
                case 1:
                    res_string = "M";
                    break;
                case 2:
                case 4:
                    res_string= "T";
                    break;
                case 3:
                    res_string = "W";
                    break;
                case 5:
                    res_string = "F";
                    break;
                case 6:
                case 7:
                    res_string = "S";
                    break;
                default:
                    return "getDay";
            }
            return res_string;

        }
    }
}

