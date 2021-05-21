package com.example.newsfeed;


import android.icu.text.DateFormat;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String DateToTimeFormat(String oldstringDate) {
        PrettyTime p = new PrettyTime();
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
//        if(isTime != null)
//            return isTime;
//        else
//            return "";
        return "";
    }

    public static String DateFormat(String oldstringDate) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", Locale.getDefault());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = sdf.parse(oldstringDate);
            newDate = dateFormat.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }
        return newDate;
    }

    public static String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}