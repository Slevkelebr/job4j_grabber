package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.model.Post;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс показывает как распарсить HTML страницу используя JSOUP.
 *
 * @author Sergey Frolov
 * @version 3.0
 * @since 16.10.2020
 */

public class SqlRuParse {

    private static final Pattern PATTERN = Pattern.compile("\\d{2}\\s\\D{3}\\s\\d{2}.\\s\\d{2}.\\d{2}");

    private static Post createPost() throws IOException, ParseException {
        String url = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        Document doc = Jsoup.connect(url).get();
        Elements vacancy = doc.select(".messageHeader");
        Elements textVacancy = doc.select(".msgBody");
        Elements msgFooter = doc.select("td.msgFooter");
        String date = msgFooter.get(1).text();
        String newDate = getDate(date);
        return new Post(1, vacancy.get(1).text(), textVacancy.get(1).text(),
                url, formatDate(replaceMonthOrDate(newDate)));
    }

    private static String getDate(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return str;
    }

    public static void main(String[] args) throws Exception {
        Post post = createPost();
        System.out.println(post);
        String url = "https://www.sql.ru/forum/job-offers/1";
        Document doc = Jsoup.connect(url).get();
        Elements table = doc.select(".sort_options");
        Elements listPages = table.select("td[style=text-align:left]");
        Elements links = listPages.select("a");
        for (Element link : links) {
            parseHtml(url);
            url = link.attr("href");
            System.out.println("Следующая страница: ======================================== " + link.text());
            if (Integer.parseInt(link.text()) > 5) {
                break;
            }
        }
    }

/*    private static Elements getListPages(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements table = doc.select(".sort_options");
        Elements listPages = table.select("td[style=text-align:left]");
        return listPages.select("a");
    }*/

    /**
     * Метод парсит HTML страницу.
     * @param url ссыдка на HTML страницу.
     * @throws ParseException
     * @throws IOException
     */
    private static void parseHtml(String url) throws ParseException, IOException {
        Document doc = Jsoup.connect(url).get();
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

    /**
     * Форматирует дату после парсинга в удобный вид для БД.
     * @param oldDate дата после парсинга.
     * @return отформатированная дата.
     * @throws ParseException
     */
    private static String formatDate(String oldDate) throws ParseException {
        Date date = new SimpleDateFormat("dd MMM yy, H:m").parse(oldDate);
        return new SimpleDateFormat("dd-MM-yyyy H:m").format(date);
    }

    /**
     * Форматирует месяц дата после парсинга.
     * @param date дата после парсинга.
     * @return дата с отформатированным месяцем.
     */
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
        } else if (str[1].equals("май")) {
            return date;
        } else {
            str[1] = str[1] + ".";
        }
        return String.join(" ", str);
    }

}