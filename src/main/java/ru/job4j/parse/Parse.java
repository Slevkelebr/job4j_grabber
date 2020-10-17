package ru.job4j.parse;

import ru.job4j.model.Post;

/**
 * Интерфейс описывающий парсинг сайта.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 17.10.2020
 */

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface Parse {
    /**
     * Метод загружает список всех постов.
     * @param link ссылка.
     * @return список постов.
     */
    List<Post> list(String link) throws IOException, ParseException;

    /**
     * Метод загружает детали одного поста.
     * @param link ссылка на пост.
     * @return пост.
     */
    Post detail(String link) throws IOException, ParseException;
}
