package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.order.Order;

import java.util.List;

@Command(name = "updateOrder", description = "Update order. id, description, amount")
public class UpdateOrder implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(UpdateOrder.class);
    private static final int ARGS_NEEDED = 3;
    @Arguments(description = "Order args")
    private List<String> args;
    @Override
    public void run() {
        if (args.size() != ARGS_NEEDED){
            log.warn("Wrong arguments!");
            return;
        }

        var result = DbService.getService().getOrderService().updateOrder(
                new Order(Integer.parseInt(args.get(0)), args.get(1), Integer.parseInt(args.get(2)))
        );

        log.info("Updated order: {}{}", System.lineSeparator(), result);
    }
}
