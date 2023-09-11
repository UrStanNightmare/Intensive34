package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.order.Order;

import java.util.List;

@Command(name = "createOrder", description = "Create order. description, amount")
public class CreateOrder implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CreateOrder.class);
    private static final int ARGS_NEEDED = 2;
    @Arguments(description = "Order args")
    private List<String> args;

    @Override
    public void run() {
        if (args.size() != ARGS_NEEDED){
            log.warn("Wrong args!");
            return;
        }
        var result = DbService.getService().getOrderService().createOrder(
                new Order(args.get(0), Integer.parseInt(args.get(1)))
        );

        log.info("Create order: {}{}", System.lineSeparator(), result);
    }
}
