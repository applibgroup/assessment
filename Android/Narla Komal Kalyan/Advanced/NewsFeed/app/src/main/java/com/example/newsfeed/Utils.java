package com.example.newsfeed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String DateFormat(String oldstringDate) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", Locale.getDefault());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            Date date = sdf.parse(oldstringDate);
            assert date != null;
            newDate = dateFormat.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }
        return newDate;
    }

}