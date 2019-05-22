package learning.encontreitroquei.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static Date parseDate(String sdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = formatter.parse(sdate);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, ex.getCause().toString(), ex);
        }
        return date;
    }

    public static String formatDate(Date ddate) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(ddate);
    }
}
