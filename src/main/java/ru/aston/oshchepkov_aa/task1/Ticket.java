package ru.aston.oshchepkov_aa.task1;

import ru.aston.oshchepkov_aa.task1.exception.ErrorCode;
import ru.aston.oshchepkov_aa.task1.exception.TicketException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.isNull;
/**
 * Abstract ticket class.
 * Has basic data for ticket.
 */
public abstract class Ticket implements Discountable {
    protected int id;
    protected User user;
    protected BigDecimal rawPrice;

    protected Ticket(int id, User user, BigDecimal rawPrice) {
        this.id = id;
        this.user = user;
        this.rawPrice = rawPrice;

        validateBasicData();
    }

    private void validateBasicData(){
        if (isNull(user)){
            throw new TicketException(ErrorCode.USER_NOT_SPECIFIED);
        }

        if (BigDecimal.ZERO.compareTo(rawPrice) > 0){
            throw new TicketException(ErrorCode.NEGATIVE_COST);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Raw ticket price without discounts.
     * @return raw ticket price.
     */
    public BigDecimal getRawPrice() {
        return rawPrice;
    }

    public void setRawPrice(BigDecimal rawPrice) {
        this.rawPrice = rawPrice;
    }

    /**
     * A method to get final ticket price. Must be overridden in children class to use discount and other stuff.
     * @return actual ticket price according to discounts etc.
     */
    public abstract BigDecimal getFinalPrice();

    @Override
    public String toString() {
        return new StringJoiner(", ", Ticket.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("rawPrice=" + rawPrice)
                .toString();
    }
}
