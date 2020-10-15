package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            System.out.println(date.text());
            System.out.println("------------------------------------------------------------");
        }
    }
}