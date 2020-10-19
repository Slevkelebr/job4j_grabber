package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.db.PsqlStore;
import ru.job4j.db.Store;
import ru.job4j.model.Post;
import ru.job4j.parse.Parse;
import ru.job4j.parse.SqlRuParse;

import java.io.*;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * .
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 19.10.2020
 */

public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    public Store store() throws IOException {
        cfg();
        return new PsqlStore(cfg);
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    private void cfg() throws IOException {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            cfg.load(in);
        }
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
        scheduler.shutdown(true);
    }

    /**
     * Внутренний класс
     */
    public static class GrabJob implements Job {

        /**
         * Метод
         *
         * @param context - context
         * @throws JobExecutionException
         */
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("store");
            try {
                List<Post> posts = parse.list("https://www.sql.ru/forum/job-offers/");
                for (Post post : posts) {
                    store.save(post);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        grab.init(new SqlRuParse(), store, scheduler);
    }
}