package com.taranko.ticketoffice.client.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm";

    public static Date buildDateFromString(String dateStr) throws ParseException {
        return buildDateFromString(dateStr, DEFAULT_DATE_FORMAT);
    }

    public static Date buildDateFromString(String dateStr, String dateFormat) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = formatter.parse(dateStr);

        return date;
    }

    public static Date buildTimeFromString(String timeStr) throws ParseException {
        return buildTimeFromString(timeStr, DEFAULT_TIME_FORMAT);
    }

    public static Date buildTimeFromString(String timeStr, String timeFormat) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(timeFormat);
        Date time = formatter.parse(timeStr);

        return time;
    }

    public static String buildStringFromDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        String dateString = dateFormat.format(date);

        dateFormat.applyPattern("dd-MM-yyyy");
        System.out.println(dateFormat.format(date));

        return dateString;
    }

    public static String buildStringFromTime(Date time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        String timeString = dateFormat.format(time);

        return timeString;
    }

    public static Date buildDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date dateNew = calendar.getTime();

        return dateNew;
    }

   /* public static Date buildTime(int hours, int minutes, String timeFormat) {
        Calendar calendar = Calendar.getInstance();
       // calendar.setTimeOfDay();
        Date dateNew = calendar.getTime();

        DateFormat formatter = new SimpleDateFormat(timeFormat);
        Date time = formatter.

        return dateNew;
    }*/
}