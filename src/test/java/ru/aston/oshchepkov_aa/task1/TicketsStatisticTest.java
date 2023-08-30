package ru.aston.oshchepkov_aa.task1;

import org.junit.jupiter.api.Test;
import ru.aston.oshchepkov_aa.task1.cinema.CinemaTicket;
import ru.aston.oshchepkov_aa.task1.cinema.Genre;
import ru.aston.oshchepkov_aa.task1.cinema.TicketType;
import ru.aston.oshchepkov_aa.task1.statistics.TicketsStatistic;
import ru.aston.oshchepkov_aa.task1.theater.PlayStyle;
import ru.aston.oshchepkov_aa.task1.theater.TheaterTicket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.assertj.core.api.Assertions.assertThat;

class TicketsStatisticTest {
    private CinemaTicket createDefaultCinemaTicket() {
        var id = 0;
        var filmName = "TestFilm";
        var rawPrice = BigDecimal.valueOf(500);
        var ticketType = TicketType.BASIC;
        var genre = Genre.ACTION;

        var user = new User("Vasya", "Pupkin", 18);
        return new CinemaTicket(id, user, rawPrice, filmName, ticketType, genre);
    }

    private TheaterTicket createDefaultTheaterTicket() {
        var id = 0;
        var playName = "TestPlay";
        var rawPrice = BigDecimal.valueOf(500);
        var playStyle = PlayStyle.MODERN;

        var user = new User("Vasya", "Pupkin", 18);
        return new TheaterTicket(id, user, rawPrice, playName, playStyle);
    }

    private void assertThatStringContainsSequences(String testString, List<String> checkValues) {
        assertThat(testString)
                .as("Must be one line with correct data")
                .containsSequence(checkValues);
    }

    @Test
    void test_statistics_when_ticket_list_is_empty() {
        var statistics = new TicketsStatistic(new ArrayList<>());

        assertThat(statistics.calculateTotalPrice())
                .as("Get total price from empty ticket list must return 0")
                .isZero();

        assertThat(statistics.toString())
                .as("Method must return empty string")
                .isEmpty();
    }

    @Test
    void test_statistics_with_one_cinema_ticket() {
        var ticket = createDefaultCinemaTicket();
        var statistics = new TicketsStatistic(List.of(ticket));

        assertThat(ticket.getDiscountPercent())
                .as("No discount expected.")
                .isEqualTo(BigDecimal.ZERO);

        assertThat(statistics.calculateTotalPrice())
                .as("Total price must be equal to rawPrice. No discount expected.")
                .isEqualTo(ticket.getRawPrice());

        assertThatStringContainsSequences(statistics.toString(),
                List.of("CinemaTicket",
                        "id=" + ticket.getId(),
                        "name='" + ticket.getUser().getName(),
                        "surname='" + ticket.getUser().getSurname(),
                        "age=" + ticket.getUser().getAge(),
                        "rawPrice=" + ticket.getRawPrice(),
                        "filmName='" + ticket.getFilmName(),
                        "genre=" + ticket.getGenre(),
                        "ticketType=" + ticket.getTicketType())
        );
    }

    @Test
    void test_statistics_with_one_cinema_ticket_with_genre_discount() {
        var ticket = createDefaultCinemaTicket();
        ticket.setGenre(Genre.MYSTERY);

        var expectedDiscount = BigDecimal.valueOf(0.1);
        var expectedPrice = ticket.getRawPrice()
                .multiply(BigDecimal.ONE.subtract(expectedDiscount));

        var statistics = new TicketsStatistic(List.of(ticket));

        assertThat(ticket.getDiscountPercent())
                .as("Discount expected.")
                .isEqualTo(expectedDiscount);

        assertThat(statistics.calculateTotalPrice())
                .as("Discount expected.")
                .isEqualTo(expectedPrice);

        assertThatStringContainsSequences(statistics.toString(),
                List.of("CinemaTicket",
                        "id=" + ticket.getId(),
                        "name='" + ticket.getUser().getName(),
                        "surname='" + ticket.getUser().getSurname(),
                        "age=" + ticket.getUser().getAge(),
                        "rawPrice=" + ticket.getRawPrice(),
                        "filmName='" + ticket.getFilmName(),
                        "genre=" + ticket.getGenre(),
                        "ticketType=" + ticket.getTicketType())
        );
    }

    @Test
    void test_statistics_with_one_cinema_ticket_with_genre_discount_and_vip_ticket_type() {
        var ticket = createDefaultCinemaTicket();
        ticket.setGenre(Genre.MYSTERY);
        ticket.setTicketType(TicketType.VIP);
        var statistics = new TicketsStatistic(List.of(ticket));

        var expectedDiscount = BigDecimal.valueOf(0.1);
        var expectedPrice = ticket.getRawPrice()
                .multiply(BigDecimal.ONE.subtract(expectedDiscount))
                .multiply(ticket.getTicketType().getCostMultiplier());

        assertThat(ticket.getDiscountPercent())
                .as("Discount expected.")
                .isEqualTo(expectedDiscount);

        assertThat(statistics.calculateTotalPrice())
                .as("Discount expected. Vip ticket costs more than common. ")
                .isEqualTo(expectedPrice);

        assertThatStringContainsSequences(statistics.toString(),
                List.of("CinemaTicket",
                        "id=" + ticket.getId(),
                        "name='" + ticket.getUser().getName(),
                        "surname='" + ticket.getUser().getSurname(),
                        "age=" + ticket.getUser().getAge(),
                        "rawPrice=" + ticket.getRawPrice(),
                        "filmName='" + ticket.getFilmName(),
                        "genre=" + ticket.getGenre(),
                        "ticketType=" + ticket.getTicketType())
        );
    }

    @Test
    void test_statistics_with_one_theater_ticket() {
        var ticket = createDefaultTheaterTicket();
        var statistics = new TicketsStatistic(List.of(ticket));

        assertThat(ticket.getDiscountPercent())
                .as("No discount expected.")
                .isEqualTo(BigDecimal.ZERO);

        assertThat(statistics.calculateTotalPrice())
                .as("Total price must be equal to rawPrice. No discount expected.")
                .isEqualTo(ticket.getRawPrice());

        assertThatStringContainsSequences(statistics.toString(),
                List.of("TheaterTicket",
                        "id=" + ticket.getId(),
                        "name='" + ticket.getUser().getName(),
                        "surname='" + ticket.getUser().getSurname(),
                        "age=" + ticket.getUser().getAge(),
                        "rawPrice=" + ticket.getRawPrice(),
                        "playTitle='" + ticket.getPlayTitle(),
                        "playStyle=" + ticket.getPlayStyle())
        );
    }

    @Test
    void test_statistics_with_one_theater_ticket_with_retirement_discount() {
        var ticket = createDefaultTheaterTicket();
        ticket.getUser().setAge(99);
        var statistics = new TicketsStatistic(List.of(ticket));

        var expectedDiscount = BigDecimal.valueOf(0.5);
        var expectedPrice = ticket.getRawPrice().multiply(
                BigDecimal.ONE.subtract(ticket.getDiscountPercent()));

        assertThat(ticket.getDiscountPercent())
                .as("Retirement discount expected.")
                .isEqualTo(expectedDiscount);

        assertThat(statistics.calculateTotalPrice())
                .as("Total price must be equal to rawPrice. No discount expected.")
                .isEqualTo(expectedPrice);

        assertThatStringContainsSequences(statistics.toString(),
                List.of("TheaterTicket",
                        "id=" + ticket.getId(),
                        "name='" + ticket.getUser().getName(),
                        "surname='" + ticket.getUser().getSurname(),
                        "age=" + ticket.getUser().getAge(),
                        "rawPrice=" + ticket.getRawPrice(),
                        "playTitle='" + ticket.getPlayTitle(),
                        "playStyle=" + ticket.getPlayStyle())
        );
    }

    @Test
    void test_statistics_total_price_different_ticket_types() {
        var cinemaTicket = createDefaultCinemaTicket();
        var theaterTicket = createDefaultTheaterTicket();
        var statistics = new TicketsStatistic(List.of(cinemaTicket, theaterTicket));

        var expectedPrice = cinemaTicket.getFinalPrice().add(theaterTicket.getFinalPrice());

        assertThat(statistics.calculateTotalPrice())
                .as("Should sum correctly")
                .isEqualTo(expectedPrice);
    }

    @Test
    void test_statistics_sorting_order() {
        var user1 = new User("Avraam", "Pavlov", 24);
        var user2 = new User("Daniel", "Gorockhov", 37);

        var cinemaTicket = createDefaultCinemaTicket();
        cinemaTicket.setUser(user1);
        var theaterTicket = createDefaultTheaterTicket();
        theaterTicket.setId(1);
        theaterTicket.setUser(user2);

        var statistics = new TicketsStatistic(List.of(cinemaTicket, theaterTicket));
        var stringTokenizer = new StringTokenizer(statistics.toString(), System.lineSeparator());

        assertThat(stringTokenizer.countTokens())
                .as("Must have 2 ticket records")
                .isEqualTo(2);

        var string1 = stringTokenizer.nextToken();
        var string2 = stringTokenizer.nextToken();

        assertThat(string1)
                .as("First record must be Daniel Gorockhov with id 1")
                .containsSequence("id=", "1")
                .containsSequence("surname='" + user2.getSurname());

        assertThat(string2)
                .as("Second record must be Avraam Pavlov with id 0")
                .containsSequence("id=", "0")
                .containsSequence("surname='" + user1.getSurname());

    }
}
