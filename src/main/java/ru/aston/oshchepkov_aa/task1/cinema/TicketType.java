package ru.aston.oshchepkov_aa.task1.cinema;

import java.math.BigDecimal;

/**
 * Tickets enum.
 */
public enum TicketType {
    BASIC(BigDecimal.ONE),
    VIP (BigDecimal.valueOf(1.1)),
    ULTIMATE(BigDecimal.valueOf(1.4));

    private final BigDecimal costMultiplier;

    /**
     * Ticket type constructor.
     * @param costMultiplier cost multiplier for specific type of ticket. Must be in range of [0.0,BigDecimal.maxVal]
     */
    TicketType(BigDecimal costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    public BigDecimal getCostMultiplier() {
        return costMultiplier;
    }
}
