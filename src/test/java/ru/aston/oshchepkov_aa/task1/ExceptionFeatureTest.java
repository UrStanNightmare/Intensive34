package ru.aston.oshchepkov_aa.task1;

import org.junit.jupiter.api.Test;
import ru.aston.oshchepkov_aa.task1.cinema.CinemaTicket;
import ru.aston.oshchepkov_aa.task1.cinema.Genre;
import ru.aston.oshchepkov_aa.task1.cinema.TicketType;
import ru.aston.oshchepkov_aa.task1.theater.PlayStyle;
import ru.aston.oshchepkov_aa.task1.theater.TheaterTicket;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionFeatureTest {
    @Test
    void test_ticket_exception_undefined() {
        var errCode = ErrorCode.UNDEFINED;

        assertThatThrownBy(() -> {
            throw new TicketException();
        })
                .as("is instance of RE")
                .isInstanceOf(RuntimeException.class)
                .as("Is instance of TicketException")
                .isInstanceOf(TicketException.class)
                .as("has correct description")
                .hasMessageContaining(errCode.getDescription());
    }

    @Test
    void test_create_ticket_with_null_user_throws_exception() {
        var ticketId = 1;
        var price = BigDecimal.ONE;
        var title = "title";
        var playStyle = PlayStyle.MODERN;


        var userNotSpecifiedCode = ErrorCode.USER_NOT_SPECIFIED;

        assertThatThrownBy(() -> new TheaterTicket(ticketId, null, price, title, playStyle))
                .as(userNotSpecifiedCode.getDescription())
                .isInstanceOf(TicketException.class)
                .hasMessageContaining(userNotSpecifiedCode.getDescription());
    }

    @Test
    void test_create_ticket_with_negative_price_throws_exception() {
        var ticketId = 1;
        var user = new User("Vasya", "Pupkin", 47);
        var price = new BigDecimal("-5000");
        var filmName = "film";
        var ticketType = TicketType.BASIC;
        var genre = Genre.ACTION;

        var negativePriceCode = ErrorCode.NEGATIVE_COST;

        assertThatThrownBy(() -> new CinemaTicket(ticketId, user, price, filmName, ticketType, genre))
                .as(negativePriceCode.getDescription())
                .isInstanceOf(TicketException.class)
                .hasMessageContaining(negativePriceCode.getDescription());
    }
}
