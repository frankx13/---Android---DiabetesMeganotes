package com.studio.neopanda.diabetesmeganotes.utils;

import java.sql.Date;

public class DateUtils {

    public static String calculateDateOfToday() {
        long millis = System.currentTimeMillis();
        Date todayDate = new Date(millis);
        return String.valueOf(todayDate);
    }

    public static String calculateDateFromToday(int daysFromToday) {
        long millis = System.currentTimeMillis();
        java.sql.Date todayDate = new java.sql.Date(millis);
        java.sql.Time nowTime = new java.sql.Time(millis);

        String[] todayDateSplit = todayDate.toString().trim().split("-", 3);

        int year = Integer.valueOf(todayDateSplit[0]);
        int month = Integer.valueOf(todayDateSplit[1]);
        int day = Integer.valueOf(todayDateSplit[2]);

        int yearsToReach;
        int monthsToReach;
        int daysToReach;

        int daysCounter = daysFromToday;
        int monthsCounter = 0;
        int yearsCounter = 0;

        if (daysFromToday >= 365) {
            yearsCounter = daysFromToday / 365;
            daysCounter = daysCounter - (365 * yearsCounter);
            daysFromToday = daysFromToday - (365 * yearsCounter);
        }
        if (daysFromToday > 30) {
            monthsCounter = daysCounter / 30;
            daysCounter = daysCounter - (30 * monthsCounter);
        }

        if (yearsCounter > 0) {
            yearsToReach = Integer.valueOf(year) - yearsCounter;
        } else {
            yearsToReach = year;
        }
        if (monthsCounter > 0) {
            monthsToReach = Integer.valueOf(month) - monthsCounter;
            if (monthsToReach < 0) {
                monthsToReach = monthsToReach + 12;
            }
        } else {
            monthsToReach = month;
        }
        if (daysCounter > 0) {
            daysToReach = day - daysCounter;
            if (daysToReach <= 0) {
                monthsToReach = monthsToReach - 1;
                daysToReach = daysToReach + 30;
            }
        } else {
            daysToReach = day;
        }

        String result;

        if (monthsToReach < 10) {
            if (daysToReach < 10) {
                result = yearsToReach + "-" + 0 + monthsToReach + "-" + 0 + daysToReach;
            } else {
                result = yearsToReach + "-" + 0 + monthsToReach + "-" + daysToReach;
            }
        } else if (daysToReach < 10) {
            result = yearsToReach + "-" + monthsToReach + "-" + 0 + daysToReach;
        } else {
            result = yearsToReach + "-" + monthsToReach + "-" + daysToReach;
        }

        return result;
    }
}
