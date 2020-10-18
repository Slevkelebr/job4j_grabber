package ru.job4j.model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Модель данных.
 *
 * @author Sergey Frolov
 * @version 2.0
 * @since 17.10.2020
 */

public class Post {

    private int id;
    private String nameVacancy;
    private String textVacancy;
    private String linkDesc;
    private Timestamp date;

    public Post() {

    }

    public Post(String nameVacancy, String textVacancy, String linkDesc, Timestamp date) {
        this.nameVacancy = nameVacancy;
        this.textVacancy = textVacancy;
        this.linkDesc = linkDesc;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameVacancy() {
        return nameVacancy;
    }

    public void setNameVacancy(String nameVacancy) {
        this.nameVacancy = nameVacancy;
    }

    public String getTextVacancy() {
        return textVacancy;
    }

    public void setTextVacancy(String textVacancy) {
        this.textVacancy = textVacancy;
    }

    public String getLinkDesc() {
        return linkDesc;
    }

    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(linkDesc, post.linkDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkDesc);
    }

    @Override
    public String toString() {
        return String.format("nameVacancy: %s, date: %s", nameVacancy, date);
    }
}
