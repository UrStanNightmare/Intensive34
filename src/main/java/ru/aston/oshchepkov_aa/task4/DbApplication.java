package ru.aston.oshchepkov_aa.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.cli.CommandLineRunner;

public class DbApplication {
    private static final Logger log = LoggerFactory.getLogger(DbApplication.class);
    public static void main(String[] args) {
        log.info("Application start.");
        try (var dbService = DbService.getService()) {
            CommandLineRunner.getRunner().start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Application end.");
    }
}
