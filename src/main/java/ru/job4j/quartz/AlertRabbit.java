package ru.job4j.quartz;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Класс показывает как работает quartz scheduler.
 *
 * @author Sergey Frolov
 * @version 1.0
 * @since 14.10.2020
 */

public class AlertRabbit {

    private static final Logger LOG = LogManager.getLogger(AlertRabbit.class.getName());

    public static void main(String[] args) {
        AlertRabbit alertRabbit = new AlertRabbit();
        Properties properties = alertRabbit.getProperties();
        try (Connection connection = alertRabbit.getConnection(properties)) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(properties.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException | SQLException | ClassNotFoundException | InterruptedException se) {
            LOG.error(se.getMessage(), se);
        }
    }

    /**
     * Метод возвращает готовый Properties.
     * @return - properties.
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties.load(in);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return properties;
    }

    /**
     * Метод возвращает соединение с конфигурацие для postgreSQL.
     * @param properties - properties.
     * @return - connection.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private Connection getConnection(Properties properties) throws SQLException, ClassNotFoundException {
        Class.forName(properties.getProperty("driver-class-name"));
        return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password")
        );
    }

    /**
     * Внутренний статический класс показывает, как работает планировщик.
     */
    public static class Rabbit implements Job {

        /**
         * Метод выполняет работу планировщика.
         *
         * @param context - context
         * @throws JobExecutionException
         */
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO rabbit (created_date) VALUES (?)")) {
                statement.setDate(1, new Date(System.currentTimeMillis()));
                statement.executeUpdate();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}