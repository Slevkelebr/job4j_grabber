package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Класс показывает как распарсить HTML страницу используя JSOUP.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 15.10.2020
 */

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Element date = td.parent().child(5);
            System.out.println(formatDate(replaceMonthOrDate(date.text())));
            System.out.println("------------------------------------------------------------");
        }
    }

    private static String formatDate(String oldDate) throws ParseException {
        Date date = new SimpleDateFormat("dd MMM yy, H:m").parse(oldDate);
        return new SimpleDateFormat("dd-MM-yyyy H:m").format(date);
    }

    private static String replaceMonthOrDate(String date) {
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
        } else {
            str[1] = str[1] + ".";
        }
        return String.join(" ", str);
    }

}