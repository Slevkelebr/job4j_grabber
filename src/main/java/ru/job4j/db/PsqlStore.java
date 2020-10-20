package ru.job4j.db;

import ru.job4j.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс реализует работу с DB PostgresSql.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 18.10.2020
 */

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement("INSERT INTO post (name, text, link, created) VALUES (?, ?, ?, ?) ON CONFLICT (link) DO NOTHING",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getNameVacancy());
            statement.setString(2, post.getTextVacancy());
            statement.setString(3, post.getLinkDesc());
            statement.setTimestamp(4, post.getDate());
            statement.executeUpdate();
            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                while (generatedKey.next()) {
                    post.setId(generatedKey.getInt(1));
                }
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post(
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created")
                    );
                    post.setId(resultSet.getInt("id"));
                    posts.add(post);
                }
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post post = new Post();
        try (PreparedStatement statement = cnn.prepareStatement("SELECT * FROM post AS p WHERE p.id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    post = new Post(
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created")
                    );
                    post.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}
