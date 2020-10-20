package ru.job4j.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.model.Post;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс показывает как распарсить HTML страницу используя JSOUP.
 *
 * @author Sergey Frolov
 * @version 4.0
 * @since 17.10.2020
 */

public class SqlRuParse implements Parse {

    private final FormatDate fd = new FormatDate();

    /**
     * Создаёт объект Поста Вакансии.
     * @param url ссылка пост с вакансией.
     * @return объект вакансии.
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public Post detail(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        Elements vacancy = doc.select(".messageHeader");
        if (!vacancy.get(0).text().contains("Java") || vacancy.get(0).text().contains("JavaScrip")) {
            return new Post();
        }
        Elements textVacancy = doc.select(".msgBody");
        Elements msgFooter = doc.select("td.msgFooter");
        String date = fd.getDateForPost(msgFooter.get(0).text());
        String formatDate = fd.formatDate(date);
        return new Post(vacancy.get(0).text(), textVacancy.get(1).text(),
                url, Timestamp.valueOf(LocalDateTime.parse(formatDate, DateTimeFormatter.ofPattern("dd-MM-yyyy H:m"))));
    }

    /**
     * Метод парсит HTML страницу.
     * @param url ссыдка на HTML страницу.
     * @throws ParseException
     * @throws IOException
     */
    @Override
    public List<Post> list(String url) throws IOException, ParseException {
        List<Post> listPost = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (int i = 3; i < row.size(); i++) {
            Element href = row.get(i).child(0);
            listPost.add(detail(href.attr("href")));
        }
        return listPost;
    }

}