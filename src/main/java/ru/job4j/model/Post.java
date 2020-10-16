package ru.job4j.model;

import java.util.Calendar;

/**
 * Модель данных.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 16.10.2020
 */

public class Post {

    private int id;
    private String nameVacancy;
    private String textVacancy;
    private String linkDesc;
    private String date;

    public Post(int id, String nameVacancy, String textVacancy, String linkDesc, String date) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
