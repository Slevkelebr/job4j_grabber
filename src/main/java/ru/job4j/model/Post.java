package ru.job4j.model;

import java.time.LocalDateTime;

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
    private LocalDateTime date;

    public Post(String nameVacancy, String textVacancy, String linkDesc, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{"
                 + "id=" + id
                + ", nameVacancy='" + nameVacancy + '\''
                + ", textVacancy='" + textVacancy + '\''
                + ", linkDesc='" + linkDesc + '\''
                + ", date='" + date + '\''
                + '}';
    }
}