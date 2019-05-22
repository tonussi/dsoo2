package util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class Util {

    public static String formatHorario(Date horario) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(horario);
    }

    public static Date parseHorario(String horario) {
        LocalTime lt = LocalTime.parse(horario);
        Instant instant = lt.atDate(LocalDate.ofYearDay(2019, 1)).atZone(ZoneId.systemDefault()).toInstant();
        Date time = Date.from(instant);
        return time;
    }
}
