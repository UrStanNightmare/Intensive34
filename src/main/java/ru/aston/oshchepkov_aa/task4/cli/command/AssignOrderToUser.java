package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;

import java.util.List;

@Command(name = "assignOrder", description = "Assign order. userId, orderId.")
public class AssignOrderToUser implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(AssignOrderToUser.class);
    private static final int ARGS_NEEDED = 2;

    @Arguments(description = "Order args")
    private List<String> args;
    @Override
    public void run() {
        if (args.size() != ARGS_NEEDED){
            log.warn("Wrong args!");
            return;
        }
        var result = DbService.getService().getOrderService()
                .assignOrderToUserByIds(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));

        log.info("Assigned order: {}{}", System.lineSeparator(), result);
    }
}
