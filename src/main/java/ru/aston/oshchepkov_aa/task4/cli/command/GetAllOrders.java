package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;
import ru.aston.oshchepkov_aa.task4.order.Order;

import java.util.List;

@Command(name = "allOrders", description = "Get all orders")
public class GetAllOrders implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(GetAllOrders.class);
    @Override
    public void run() {
        List<Order> allOrders = DbService.getService().getOrderService().getAll();

        var builder = new StringBuilder()
                .append("Got orders:").append(System.lineSeparator());

        for (Order o : allOrders) {
            builder.append(o).append(System.lineSeparator());
        }

        log.info("{}", builder);
    }
}
