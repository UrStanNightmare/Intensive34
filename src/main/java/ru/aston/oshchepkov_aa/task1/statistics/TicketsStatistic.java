package ru.aston.oshchepkov_aa.task1.statistics;

import ru.aston.oshchepkov_aa.task1.Ticket;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketsStatistic implements TotalPriceCalculation {
    private static final Comparator<Ticket> surnameAscComparator = Comparator
            .comparing(u -> u.getUser().getSurname());
    private final Optional<List<Ticket>> tickets;

    public TicketsStatistic(List<Ticket> tickets) {
        this.tickets = Optional.ofNullable(tickets);
    }

    @Override
    public String toString() {
        return this.tickets.map(ticketList ->
                ticketList.stream()
                        .sorted(surnameAscComparator)
                        .map(Ticket::toString)
                        .collect(Collectors.joining(System.lineSeparator()))
        ).orElse("");
    }

    @Override
    public BigDecimal calculateTotalPrice() {
        return this.tickets.map(ticketList ->
                ticketList.stream()
                        .map(Ticket::getFinalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        ).orElse(BigDecimal.ZERO);
    }

}
