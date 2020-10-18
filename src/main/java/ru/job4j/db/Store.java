package ru.job4j.db;

import ru.job4j.model.Post;

/**
 * Интерфейс для связи с БД.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 17.10.2020
 */

import java.util.List;

public interface Store {

    void save(Post post);

    List<Post> getAll();

    Post findById(String id);
}