package ru.job4j.parse;

import org.junit.Test;
import ru.job4j.db.ConnectionRollback;
import ru.job4j.db.PsqlStore;
import ru.job4j.model.Post;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SqlRuParseTest {

    public Connection init() {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void savePost() throws Exception {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post = new Post("Java dev", "We need a Java developer", "link", Timestamp.valueOf(LocalDateTime.now()));
            store.save(post);
            assertThat(store.findById(String.valueOf(post.getId())).getNameVacancy(), is("Java dev"));
        }
    }

    @Test
    public void getAllPosts() throws Exception {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post = new Post("Java", "We need a Java developer", "link2", Timestamp.valueOf(LocalDateTime.now()));
            store.save(post);
            assertThat(store.findById(String.valueOf(post.getId())).getLinkDesc(), is("link2"));
        }
    }

    @Test
    public void findByIdPost() throws Exception {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post = new Post("Java dev", "We need a Java developer", "link45", Timestamp.valueOf(LocalDateTime.now()));
            store.save(post);
            assertThat(store.findById(String.valueOf(post.getId())).getId(), is(post.getId()));
        }
    }

}