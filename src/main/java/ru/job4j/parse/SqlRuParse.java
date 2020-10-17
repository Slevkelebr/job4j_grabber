package ru.job4j.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.model.Post;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс показывает как распарсить HTML страницу используя JSOUP.
 *
 * @author Sergey Frolov
 * @version 4.0
 * @since 17.10.2020
 */

public class SqlRuParse {

    private final FormatDate fd = new FormatDate();

    /**
     * Создаёт объект Поста Вакансии.
     * @param url ссылка пост с вакансией.
     * @return объект вакансии.
     * @throws IOException
     * @throws ParseException
     */
    public Post createPost(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        Elements vacancy = doc.select(".messageHeader");
        Elements textVacancy = doc.select(".msgBody");
        Elements msgFooter = doc.select("td.msgFooter");
        String date = fd.getDateForPost(msgFooter.get(1).text());
        String formatDate = fd.formatDate(date);
        return new Post(vacancy.get(1).text(), textVacancy.get(1).text(),
                url, LocalDateTime.parse(formatDate, DateTimeFormatter.ofPattern("dd-MM-yyyy H:m")));
    }

    /**
     * Метод парсит HTML страницу.
     * @param url ссыдка на HTML страницу.
     * @throws ParseException
     * @throws IOException
     */
    private void parseHtml(String url) throws ParseException, IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Element date = td.parent().child(5);
            System.out.println(fd.formatDate(date.text()));
            System.out.println("------------------------------------------------------------");
        }
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse sql = new SqlRuParse();
        String url = "https://www.sql.ru/forum/job-offers/";
        for (int i = 1; i <= 5; i++) {
            sql.parseHtml(url + i);
        }
    }

}