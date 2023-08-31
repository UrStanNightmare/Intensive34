package ru.aston.oshchepkov_aa.task1.theater;

import ru.aston.oshchepkov_aa.task1.Ticket;
import ru.aston.oshchepkov_aa.task1.User;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class TheaterTicket extends Ticket {
    private static final int RETIREMENT_AGE = 50;
    private String playTitle;
    private PlayStyle playStyle;

    public TheaterTicket(int id, User user, BigDecimal rawPrice, String playTitle, PlayStyle playStyle) {
        super(id, user, rawPrice);
        this.playTitle = playTitle;
        this.playStyle = playStyle;
    }

    public String getPlayTitle() {
        return playTitle;
    }

    public void setPlayTitle(String playTitle) {
        this.playTitle = playTitle;
    }

    public PlayStyle getPlayStyle() {
        return playStyle;
    }

    public void setPlayStyle(PlayStyle playStyle) {
        this.playStyle = playStyle;
    }

    @Override
    public BigDecimal getFinalPrice() {
        return rawPrice.multiply(BigDecimal.ONE.subtract(getDiscountPercent()));
    }

    /**
     * A method to get discount percent.
     * Adds discount on retirement age.
     * @return actual ticket price
     */
    @Override
    public BigDecimal getDiscountPercent() {
        if (user.getAge() >= RETIREMENT_AGE) {
            return new BigDecimal("0.5");
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TheaterTicket.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("rawPrice=" + rawPrice)
                .add("playTitle='" + playTitle + "'")
                .add("playStyle=" + playStyle)
                .toString();
    }
}
