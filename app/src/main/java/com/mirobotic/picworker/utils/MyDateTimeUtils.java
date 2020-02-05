package com.mirobotic.picworker.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyDateTimeUtils {

    private static final String TAG = MyDateTimeUtils.class.getSimpleName();

    public static String getCurrentTime() {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date());
    }

    public static String formatDate(String date, DateFormat dateFormat) {

        if (date == null || date.isEmpty()) {
            return "";
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (parsedDate == null) {
            return "";
        }

        return dateFormat.format(parsedDate);
    }

    public static String printDate(boolean showTime, String dateToFormat) {
        //2019-12-06T10:02:18Z
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        try {

            Date date = dateFormat.parse(dateToFormat);
            DateFormat returnFormat;

            if (showTime) {
                returnFormat = new SimpleDateFormat("dd MMM yy hh:mm a", Locale.ENGLISH);
            }else {
                returnFormat = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
            }

            if (date != null) {
                return returnFormat.format(date);
            }

        } catch (Exception e0) {

            try {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
                Date date = dateFormat.parse(dateToFormat);

                DateFormat returnFormat;
                if (showTime) {
                    returnFormat = new SimpleDateFormat("dd MMM yy hh:mm a", Locale.ENGLISH);
                }else {
                    returnFormat = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
                }

                if (date != null) {
                    return returnFormat.format(date);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }



        }

        return dateToFormat;
    }

    public static String date2DayTime(Date nowTime, Date oldTime) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(nowTime);
            Calendar oldCal = Calendar.getInstance();
            oldCal.setTime(oldTime);

            int oldYear = oldCal.get(Calendar.YEAR);
            int year = cal.get(Calendar.YEAR);
            int oldDay = oldCal.get(Calendar.DAY_OF_YEAR);
            int day = cal.get(Calendar.DAY_OF_YEAR);

            if (oldYear == year) {
                int value = oldDay - day;
                if (value == -1) {
                    return "Yesterday " + timeFormat.format(oldTime);
                } else if (value == 0) {
                    return "Today " + timeFormat.format(oldTime);
                } else if (value == 1) {
                    return "Tomorrow " + timeFormat.format(oldTime);
                } else {
                    return dateFormat.format(oldTime) + " " + timeFormat.format(oldTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat.format(oldTime) + " " + timeFormat.format(oldTime);
    }

    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getLastDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getDate(@NotNull String date, @NotNull SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMonth(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            return "0" + month;
        } else {
            return "" + month;
        }
    }

    public static String getDay(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        } else {
            return "" + day;
        }
    }

    public String formatDate(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public String getDate(int dayOfMonth, int month, int year) {
        Date date;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = dateFormat.parse(year + "-" + month + "-" + dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }

        if (date == null) {
            Log.e(MyDateTimeUtils.class.getSimpleName(), "date is null");
            return "";
        }

        String mDay, mMonth;

        if (month < 10) {
            mMonth = "0" + month;
        } else {
            mMonth = "" + month;
        }


        if (dayOfMonth < 10) {
            mDay = "0" + dayOfMonth;
        } else {
            mDay = "" + dayOfMonth;
        }

        return year + "-" + mMonth + "-" + mDay + " 00:00:00";
    }

    public String getDateDiff(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String mDay, mMonth;
        cal.add(Calendar.DATE, days);
        if (((cal.get(Calendar.MONTH) + 1) < 10)) {
            mMonth = "0" + ((cal.get(Calendar.MONTH) + 1));
        } else {
            mMonth = "" + ((cal.get(Calendar.MONTH)) + 1);
        }

        if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
            mDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
        } else {
            mDay = "" + cal.get(Calendar.DAY_OF_MONTH);
        }

        return cal.get(Calendar.YEAR) + "-" + mMonth + "-" + mDay;

    }

    public static long getDateDiff(String date) {
        long diff = 0;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Date dueDate = dateFormat.parse(date);
            Date now = new Date();

            if (dueDate != null) {
                long d = now.getTime() - dueDate.getTime();
                diff =  TimeUnit.DAYS.convert(d, TimeUnit.MILLISECONDS);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Date", "Diff: "+diff);
        return diff;
    }

    public static String getMonthName(String number){

        switch (number){
            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
        }

        return number;

    }

    public static String getGreetingMessage() {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour > 19 ) {
            return "Night";
        }else if (hour > 15) {
            return "Evening";
        }else if (hour > 12) {
            return "Afternoon";
        }else if (hour > 3) {
            return "Morning";
        }else {
            return "Night";
        }


    }


    public static long getDaysFromDate( String dueDate) {

        long days = 0;

        if (dueDate == null) {
            return days;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = dateFormat.parse(dueDate);

            if (date!=null) {
                long diff = new Date().getTime() - date.getTime();
                days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return days;
    }

    public static String getFYStartDate() {

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        String dateStart;
        if (month > 2) {
            dateStart = ""+(calendar.get(Calendar.YEAR))+"-04-01";
        } else {
            dateStart = ""+(calendar.get(Calendar.YEAR) - 1)+"-04-01";
        }

        return dateStart;
    }

    public static String getFYEndDate() {

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        String dateEnd;
        if (month > 2) {
            dateEnd = ""+(calendar.get(Calendar.YEAR) + 1)+"-03-31";
        } else {
            dateEnd = ""+(calendar.get(Calendar.YEAR))+"-03-31";
        }

        return dateEnd;
    }

    public static String getCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.ENGLISH);
        return dateFormat.format(new Date());
    }

    public static String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        return dateFormat.format(new Date());
    }
}
