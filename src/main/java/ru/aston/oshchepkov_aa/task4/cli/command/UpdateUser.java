package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.user.User;

import java.util.List;

@Command(name = "updateUser", description = "Update user. id, firstName, lastName, middleName, phone number, email")
public class UpdateUser implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(UpdateUser.class);
    private static final int ARGS_NEEDED = 6;
    @Arguments(description = "User args")
    private List<String> args;
    @Override
    public void run() {
        if (args.size() != ARGS_NEEDED){
            log.warn("Wrong arguments!");
            return;
        }

        var result = DbService.getService().getUserService().updateUser(
                new User(Integer.parseInt(args.get(0)), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5))
        );

        log.info("Updated user: {}{}", System.lineSeparator(), result);
    }
}
