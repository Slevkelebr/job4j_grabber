package ru.job4j.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Форматирование даты.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 17.10.2020
 */

public class FormatDate {

    private final Pattern pattern = Pattern.compile("\\d{2}\\s\\D{3}\\s\\d{2}.\\s\\d{2}.\\d{2}");

    /**
     * Класс сопоставляет строку для получения даты в формате {@code dd MMM yy, H:m}.
     * @param str строка.
     * @return дата.
     */
    public String getDateForPost(String str) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return str;
    }

    /**
     * Форматирует дату после парсинга в формате {@code dd-MM-yyyy H:m}.
     * @param oldDate дата после парсинга.
     * @return отформатированная дата.
     * @throws ParseException
     */
    public String formatDate(String oldDate) throws ParseException {
        String newDate = replaceMonthOrDate(oldDate);
        Date date = new SimpleDateFormat("dd MMM yy, H:m").parse(newDate);
        return new SimpleDateFormat("dd-MM-yyyy H:m").format(date);
    }

    /**
     * Форматирует месяц дата после парсинга.
     * @param date дата после парсинга.
     * @return дата с отформатированным месяцем.
     */
    private String replaceMonthOrDate(String date) {
        Date today = new Date();
        Calendar day = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy, H:m");
        String[] str = date.split(" ");
        if (str[0].contains("сегодня")) {
            str[0] = formatter.format(today);
            str[1] = "";
        } else if (str[0].contains("вчера")) {
            day.add(Calendar.DAY_OF_YEAR, -1);
            Date yesterday = day.getTime();
            str[0] = formatter.format(yesterday);
            str[1] = "";
        } else if (str[1].equals("сен")) {
            str[1] = "сент.";
        } else if (str[1].equals("май")) {
            return date;
        } else {
            str[1] = str[1] + ".";
        }
        return String.join(" ", str);
    }
}
