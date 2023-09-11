package ru.aston.oshchepkov_aa.task4.cli.command;

import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.types.numerics.DefaultNumericConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.DbService;

@Command(name = "deleteOrderById", description = "Delete order by id.")
public class DeleteOrderById implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(DeleteOrderById.class);
    @Arguments(description = "order id", typeConverterProvider = DefaultNumericConverter.class)
    private int id;
    @Override
    public void run() {
        var result = DbService.getService().getOrderService().deleteOrderById(id);

        log.info("Deleted order: {}{}", System.lineSeparator(), result);
    }
}
