package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.types.numerics.DefaultNumericConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;

@Command(name = "deleteUserById", description = "Delete user by id.")
public class DeleteUserById implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(DeleteUserById.class);
    @Arguments(description = "user id", typeConverterProvider = DefaultNumericConverter.class)
    private int id;
    @Override
    public void run() {
        var result = DbService.getService().getUserService().deleteUserById(id);

        log.info("Deleted user: {}{}", System.lineSeparator(), result);
    }
}
