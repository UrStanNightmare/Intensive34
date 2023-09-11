package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.types.numerics.DefaultNumericConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.user.User;

@Command(name = "userById", description = "Get user by id.")
public class UserById implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(UserById.class);
    @Arguments(description = "user id", typeConverterProvider = DefaultNumericConverter.class)
    private int id;
    @Override
    public void run() {
        User userById = DbService.getService().getUserService().findUserById(id);

        log.info("Found user: {}{}", System.lineSeparator(), userById);
    }
}
