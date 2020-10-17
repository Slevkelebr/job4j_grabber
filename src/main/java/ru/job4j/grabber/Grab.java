package ru.job4j.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.job4j.db.Store;
import ru.job4j.parse.Parse;

/**
 * Интерфейс для работы с библиотекой quartz.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 17.10.2020
 */

public interface Grab {
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}