package ObjectManagers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    public static String getDayStringOld(Date date) {
        Locale locale = new Locale("pl","PL");
        DateFormat formatter = new SimpleDateFormat("EEE", locale);
        return formatter.format(date);
    }

}
