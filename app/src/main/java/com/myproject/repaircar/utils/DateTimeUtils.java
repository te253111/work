package com.myproject.repaircar.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class DateTimeUtils {

    public static final String DT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_mm_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_mm_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_mm_dd_HH_dot_mm = "yyyy-MM-dd HH.mm";
    public static final String dd_MM_yyyy_HH_mm = "dd/MM/yyyy HH:mm";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String dd_MM_yyyy = "dd-MM-yyyy";
    public static final String dd_MM_yyyy_2 = "dd/MM/yyyy";
    public static final String d_M_yyyy_H_mm = "d/M/yyyy H:mm";
    public static final String HH_mm = "HH:mm";
    public static final String HH_dot_mm = "HH.mm";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String yyMM = "yyMM";
    public static final String yyyy = "yyyy";
    public static final String mm = "mm";
    public static final String MM = "MM";
    public static final String FORMAT_STANDARD = "yyyy-MM-dd HH:mmZ";



    //:=*===================*===============*=======================================
    public static String
    getToday(
    ) {
        Calendar w_cal = Calendar.getInstance();
        //.Get today
        String w_strToday =
                String.format("%1$ta", w_cal) + ", "
                        + String.format(Locale.ENGLISH, "%1$tb", w_cal) + " "
                        + w_cal.get(Calendar.DATE) + ", "
                        + w_cal.get(Calendar.YEAR);
        return w_strToday;
    }

    //:=*===================*===============*=======================================
    public static String
    getDate(
            String p_strFormat
    ) {
        Calendar w_cal = Calendar.getInstance();
        SimpleDateFormat w_sdf = new SimpleDateFormat(p_strFormat);
        return w_sdf.format(w_cal.getTime());
    }

    public static String getCurrentDateTime(){
        return toFormat(new Date(),yyyy_mm_dd_HH_mm_ss);
    }

    //:=*===================*===============*=======================================
    public static String
    getEngDate(
            String p_strFormat
    ) {
        Calendar w_cal = Calendar.getInstance();
        SimpleDateFormat w_sdf = new SimpleDateFormat(p_strFormat, Locale.US);
        return w_sdf.format(w_cal.getTime());
    }

    //:=*===================*===============*=======================================
    public static String
    getEngDate(
            String p_strFormat, Date date
    ) {
        if (date == null) date = new Date();
        Calendar w_cal = Calendar.getInstance();
        w_cal.setTime(date);
        SimpleDateFormat w_sdf = new SimpleDateFormat(p_strFormat, Locale.US);
        return w_sdf.format(w_cal.getTime());
    }

    //:=*===================*===============*=======================================
    public static String
    getNow(
    ) {
        Calendar w_cal = Calendar.getInstance();
        //.Get now time
        String w_strNow =
                String.format("%1$02d", w_cal.get(Calendar.HOUR_OF_DAY)) + ":"
                        + String.format("%1$02d", w_cal.get(Calendar.MINUTE));
        return w_strNow;
    }

    //:=*===================*===============*=======================================
    public static String
    getNow2(
    ) {
        Calendar w_cal = Calendar.getInstance();
        //.Get now time
        String w_strNow =
                String.format("%1$02d", w_cal.get(Calendar.HOUR_OF_DAY)) + ":"
                        + String.format("%1$02d", w_cal.get(Calendar.MINUTE)) + ":"
                        + String.format("%1$02d", w_cal.get(Calendar.SECOND));
        return w_strNow;
    }

    public static String getTime(Date date) {
        Calendar w_cal = Calendar.getInstance();
        w_cal.setTime(date);
        String w_strNow =
                String.format("%1$02d", w_cal.get(Calendar.HOUR_OF_DAY)) + ":"
                        + String.format("%1$02d", w_cal.get(Calendar.MINUTE)) + ":"
                        + String.format("%1$02d", w_cal.get(Calendar.SECOND));
        return w_strNow;
    }

    //:=*===================*===============*======================================= by watt

    public static Calendar convert(Date date) {
        Calendar w_cal = Calendar.getInstance();
        w_cal.setTime(date);
        return w_cal;
    }

    //:=*===================*===============*=======================================
    public static String
    getNow3(
    ) {
        Calendar w_cal = Calendar.getInstance();
        String AmPM = (w_cal.get(Calendar.AM_PM) == Calendar.PM) ? "PM" : "AM";
        //.Get now time
        String w_strNow =
                String.format("%1$02d", w_cal.get(Calendar.HOUR_OF_DAY)) + ":"
                        + String.format("%1$02d", w_cal.get(Calendar.MINUTE));
        return w_strNow;
    }

    //:=*===================*===============*=======================================
    public static String
    toFormatDate(
            String p_strDate
    ) {
        DateFormat w_df = new SimpleDateFormat(DT_yyyy_MM_dd);
        Date w_date;
        Calendar w_cal = Calendar.getInstance();
        String w_strDate = p_strDate;
        try {
            w_date = w_df.parse(p_strDate);
            w_cal.setTime(w_date);
            //.Get today
            w_strDate =
                    String.format("%1$ta", w_cal) + ", "
                            + String.format(Locale.ENGLISH, "%1$tb", w_cal) + " "
                            + w_cal.get(Calendar.DATE) + ", "
                            + w_cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        w_df = null;
        w_cal = null;
        return w_strDate;
    }


    public static String toFormatDate(String p_strDate, String p_format) {
        DateFormat w_df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat w_toFormat = new SimpleDateFormat(p_format);
        String w_strDate = p_strDate;

        try {
            w_strDate = w_toFormat.format(w_df.parse(p_strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return w_strDate;
    }

    public static String toTime(String p_time, String p_fromFormat, String p_toFormat) {
        p_fromFormat = p_fromFormat == null ? "HH:mm:ss" : p_fromFormat;
        p_toFormat = p_toFormat == null ? "HH.mm" : p_toFormat;
        DateFormat w_df = new SimpleDateFormat(p_fromFormat);
        SimpleDateFormat w_toFormat = new SimpleDateFormat(p_toFormat);
        String w_strTime = p_time;

        try {
            w_strTime = w_toFormat.format(w_df.parse(p_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return w_strTime;
    }

    public static Date getDate(String p_srcFormat, String p_strDate) {
        p_strDate = (p_strDate == null) ? toFormat(new Date(), p_srcFormat) : p_strDate;

        DateFormat w_df = new SimpleDateFormat(p_srcFormat);
        Date w_date = null;

        try {
            w_date = w_df.parse(p_strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return w_date;
    }


    public static String toFormat(Date p_date, String p_toFormat) {
        p_date = (p_date == null) ? new Date() : p_date;

        SimpleDateFormat w_toFormat = new SimpleDateFormat(p_toFormat);
        String w_strTime = w_toFormat.format(p_date);

        return w_strTime;
    }

    public static String toFormat(Calendar calendar,String format){
        Date date = calendar.getTime();
        return toFormat(date,format);
    }

    public static Date addPeriod(Date p_currDate, int value, int p_periodType) {
        p_currDate = (p_currDate == null) ? new Date() : p_currDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(p_currDate);
        switch (p_periodType) {
            case Calendar.SECOND:
                cal.add(Calendar.SECOND, value);
                break;
            case Calendar.MINUTE:
                cal.add(Calendar.MINUTE, value);
                break;
            case Calendar.HOUR:
                cal.add(Calendar.HOUR, value);
                break;
            case Calendar.DAY_OF_MONTH:
                cal.add(Calendar.DAY_OF_MONTH, value);
                break;
        }
        p_currDate = cal.getTime();
        return p_currDate;
    }

    public static Date rollBackPeriod(Date p_currDate, int value, int p_periodType) {
        p_currDate = (p_currDate == null) ? new Date() : p_currDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(p_currDate);
        value *= -1;
        switch (p_periodType) {
            case Calendar.SECOND:
                cal.add(Calendar.SECOND, value);
                break;
            case Calendar.MINUTE:
                cal.add(Calendar.MINUTE, value);
                break;
            case Calendar.HOUR:
                cal.add(Calendar.HOUR, value);
                break;
            case Calendar.DAY_OF_MONTH:
                cal.add(Calendar.DAY_OF_MONTH, value);
                break;
        }
        p_currDate = cal.getTime();
        return p_currDate;
    }

    public static Date toStartingDate(Date p_currDate) {
        p_currDate = (p_currDate == null) ? new Date() : p_currDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(p_currDate);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 0);

        p_currDate = cal.getTime();
        return p_currDate;
    }

    public static Date toEndingDate(Date p_currDate) {
        p_currDate = (p_currDate == null) ? new Date() : p_currDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(p_currDate);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        p_currDate = cal.getTime();
        return p_currDate;
    }

    public static long compareDateTime(Date p_date1, Date p_date2) {
        long diff = p_date1.getTime() - p_date2.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }

    public static boolean equalsDate(Date date1,Date date2){
        String date1Text = toFormat(date1,yyyy_MM_dd);
        String date2Text = toFormat(date2,yyyy_MM_dd);

        return date1Text.equals(date2Text);
    }

    public static Date getYesterdayDate(Date p_currDate) {
        p_currDate = (p_currDate == null) ? new Date() : p_currDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(p_currDate);

        cal.add(Calendar.DATE, -1);

        p_currDate = cal.getTime();
        return p_currDate;
    }

    public static String toStandardFormat(Date date){
        date = (date == null) ? new Date() : date;

        return toFormat(date,FORMAT_STANDARD);
    }

    public static Date getStandardFormat(String dateTime){
        Date d = getDate(FORMAT_STANDARD, dateTime);
        return d;
    }
    public static String fromStandardFormat(String dateTime,String format){
        if(dateTime == null || dateTime.trim().length() == 0)
            return null;
        Date d = getStandardFormat(dateTime);
        return toFormat(d,format);
    }

    public static long getTimeMillisec() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public static String getDateText(int year,int month,int day,String format){
        String dateText;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);

        SimpleDateFormat w_sdf = new SimpleDateFormat(format);
        return w_sdf.format(cal.getTime());
    }

    public static Calendar getCalendar(int year,int month,int day){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,day);

        return cal;
    }

    public static Calendar getCalendar(int hour,int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);

        return cal;
    }

    public static Calendar getCalendarForTime(String time,String format){
        if(time == null)
            return null;

        Date date = getDate(format,time);

        return convert(date);
    }



    public static String getTimeText(int hour, int minute, String format){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);

        SimpleDateFormat w_sdf = new SimpleDateFormat(format);
        return w_sdf.format(cal.getTime());
    }

    public static String getYYYYMMDD(){
        return getDate(yyyy_MM_dd);
    }
    public static String getHHmm(){
        return getDate(HH_dot_mm);
    }

}
