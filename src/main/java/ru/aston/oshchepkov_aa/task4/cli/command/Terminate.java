package ru.aston.oshchepkov_aa.task4.cli.command;


import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(name = "stop", description = "Stops an application")
public class Terminate implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Terminate.class);
    @Override
    public void run() {
        log.info("Thanks for using! Terminating.");
    }
}
