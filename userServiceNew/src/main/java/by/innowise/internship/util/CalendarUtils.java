package by.innowise.internship.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    private CalendarUtils() {
    }

    public static String convertMilliSecondsToFormattedDate(Long milliSeconds) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return format.format(calendar.getTime());
    }
}
