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
    private Calendar Date;

    public Post(int id, String nameVacancy, String textVacancy, String linkDesc, Calendar date) {
        this.id = id;
        this.nameVacancy = nameVacancy;
        this.textVacancy = textVacancy;
        this.linkDesc = linkDesc;
        Date = date;
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

    public Calendar getDate() {
        return Date;
    }

    public void setDate(Calendar date) {
        Date = date;
    }
}
