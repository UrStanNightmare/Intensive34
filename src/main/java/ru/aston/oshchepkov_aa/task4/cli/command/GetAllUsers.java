package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.user.User;

import java.util.List;

@Command(name = "allUsers", description = "Get all users")
public class GetAllUsers implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(GetAllUsers.class);
    @Override
    public void run() {
        List<User> allUsers = DbService.getService().getUserService().getAll();

        var builder = new StringBuilder()
                .append("Got users:").append(System.lineSeparator());

        for (User u : allUsers) {
            builder.append(u).append(System.lineSeparator());
        }

        log.info("{}", builder);
    }
}
