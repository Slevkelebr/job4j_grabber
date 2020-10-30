package ru.job4j.parse;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class FormatDateTest {

    @Test
    public void whenDateFormatForPostTrue() {
        FormatDate formatDate = new FormatDate();
        String actual = formatDate.getDateForPost("21 окт 20, 20:46 [22218555] Ответить | Цитировать Сообщить модератору");
        String expected = "21 окт 20, 20:46";
        assertEquals(actual, expected);
    }

    @Test
    public void whenDateFormatForPostFalse() {
        FormatDate formatDate = new FormatDate();
        String actual = formatDate.getDateForPost("21 окт 2020, 20:46  Цитировать Сообщить модератору");
        String expected = "21 окт 20, 20:46";
        assertNotEquals(expected, actual);
    }

    @Test
    public void whenDateFormatTrue() throws ParseException {
        FormatDate formatDate = new FormatDate();
        String actual = formatDate.formatDate("21 окт 20, 20:46");
        String expected = "21-10-2020 20:46";
        assertEquals(actual, expected);
    }

}