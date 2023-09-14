package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.types.numerics.DefaultNumericConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.order.Order;

@Command(name = "orderById", description = "Get order by id.")
public class OrderById implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(OrderById.class);
    @Arguments(description = "user id", typeConverterProvider = DefaultNumericConverter.class)
    private int id;

    @Override
    public void run() {
        Order orderById = DbService.getService().getOrderService().findOrderById(id);

        log.info("Found order: {}{}", System.lineSeparator(), orderById);
    }
}
