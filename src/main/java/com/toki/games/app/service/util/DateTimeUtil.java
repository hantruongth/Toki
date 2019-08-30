package com.toki.games.app.service.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeUtil {

    public static Date convertToDate(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static Date parseDateTimeFromString(String dateTime){
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
            return convertToDate(localDateTime);
        }catch (DateTimeParseException e){
            e.printStackTrace();
        }
        return null;
    }


}
