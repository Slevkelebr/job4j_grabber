package ru.job4j.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Форматирование даты.
 *
 * @author Sergey Frolov
 * @version 2.0
 * @since 18.10.2020
 */

public class FormatDate {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy, HH:mm");

    private final Calendar today = Calendar.getInstance();

    private final Pattern pattern = Pattern.compile("\\d{1,2}\\s\\D{3}\\s\\d{2}.\\s\\d{2}.\\d{2}");

    private final Map<String, String> monthMapper = new HashMap<>() {
        {
            put("янв", "янв.");
            put("фев", "февр.");
            put("мар", "мар.");
            put("апр", "апр.");
            put("май", "май");
            put("июн", "июн.");
            put("июл", "июл.");
            put("авг", "авг.");
            put("сен", "сент.");
            put("окт", "окт.");
            put("ноя", "нояб.");
            put("дек", "дек.");
        }
    };

    /**
     * Класс сопоставляет строку для получения даты в формате {@code dd MMM yy, HH:mm}.
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
     * Форматирует дату после парсинга в формате {@code dd-MM-yyyy HH:mm}.
     * @param oldDate дата после парсинга.
     * @return отформатированная дата.
     * @throws ParseException
     */
    public String formatDate(String oldDate) throws ParseException {
        String newDate = replaceMonthOrDate(oldDate);
        Date date = formatter.parse(newDate);
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
    }

    private String parseToday() {
        Date day = today.getTime();
        return formatter.format(day);
    }

    private String parseYesterday() {
        today.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = today.getTime();
        return formatter.format(yesterday);
    }

    /**
     * Форматирует месяц дата после парсинга.
     * @param date дата после парсинга.
     * @return дата с отформатированным месяцем.
     */
    private String replaceMonthOrDate(String date) {
        Map<String, String> mapper = new HashMap<>() {
            {
                put("сегодня,", parseToday());
                put("вчера,", parseYesterday());
            }
        };
        String[] str = date.split(" ");
        if (monthMapper.containsKey(str[1])) {
            str[1] = monthMapper.get(str[1]);
        }
        if (mapper.containsKey(str[0])) {
            str[0] = String.valueOf(mapper.get(str[0]));
        }
        return String.join(" ", str);
    }

}
