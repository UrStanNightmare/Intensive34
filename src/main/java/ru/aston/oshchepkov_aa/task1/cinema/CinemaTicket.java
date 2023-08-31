package ru.aston.oshchepkov_aa.task1.cinema;

import ru.aston.oshchepkov_aa.task1.Ticket;
import ru.aston.oshchepkov_aa.task1.User;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class CinemaTicket extends Ticket {
    private String filmName;
    private Genre genre;
    private TicketType ticketType;

    public CinemaTicket(int id, User user, BigDecimal rawPrice, String filmName, TicketType ticketType, Genre genre) {
        super(id, user, rawPrice);
        this.filmName = filmName;
        this.genre = genre;
        this.ticketType = ticketType;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * A method to get final cinema ticket price.
     * Including ticket type price multiplier.
     * @return actual cinema ticket price.
     */
    @Override
    public BigDecimal getFinalPrice() {
        return rawPrice
                .multiply(BigDecimal.ONE.subtract(getDiscountPercent()))
                .multiply(ticketType.getCostMultiplier());
    }

    /**
     * A method to get cinema discount based on genre.
     * @return cinema ticket discount;
     */
    @Override
    public BigDecimal getDiscountPercent() {
        switch (genre) {
            case MYSTERY, DRAMA -> {
                return new BigDecimal("0.1");
            }
            case COMEDY -> {
                return new BigDecimal("0.08");
            }
            default -> {
                return BigDecimal.ZERO;
            }
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CinemaTicket.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("rawPrice=" + rawPrice)
                .add("filmName='" + filmName + "'")
                .add("genre=" + genre)
                .add("ticketType=" + ticketType)
                .toString();
    }
}
