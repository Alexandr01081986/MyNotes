package com.example.mynotes.service;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {

    @SuppressLint("SimpleDateFormat")
    public static String formatDateTimeToString(Date date) {
        String temp = "";
        if (date != null) {
            temp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(date);
        }
        return temp;
    }

    public static Date formatStringToDate(int year,int month,int day) {
        Date date = new Date(year, month, day);
        return date;
    }
}
