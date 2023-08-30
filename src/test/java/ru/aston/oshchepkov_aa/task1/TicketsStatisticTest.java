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
        var id = 0;
        var filmName = "TestFilm";
        var rawPrice = BigDecimal.valueOf(500);
        var ticketType = TicketType.BASIC;
        var genre = Genre.ACTION;

        var user = new User("Vasya", "Pupkin", 18);
        var cinemaTicket = new CinemaTicket(id, user, rawPrice, filmName, ticketType, genre);

        var statistics = new TicketsStatistic(List.of(cinemaTicket));

        assertThat(cinemaTicket.getDiscountPercent())
                .as("No discount expected.")
                .isEqualTo(BigDecimal.ZERO);

        assertThat(statistics.calculateTotalPrice())
                .as("Total price must be equal to rawPrice. No discount expected.")
                .isEqualTo(rawPrice);

        assertThat(statistics.toString())
                .as("Must be one line with correct data")
                .containsSequence("CinemaTicket")
                .containsSequence("id=" + id)
                .containsSequence("name='" + user.getName())
                .containsSequence("surname='" + user.getSurname())
                .containsSequence("age=" + user.getAge())
                .containsSequence("rawPrice=" + rawPrice)
                .containsSequence("filmName='" + filmName)
                .containsSequence("genre=" + genre)
                .containsSequence("ticketType=" + ticketType);
    }

    @Test
    void test_statistics_with_one_cinema_ticket_with_genre_discount() {
        var id = 0;
        var filmName = "TestFilm";
        var rawPrice = BigDecimal.valueOf(1000);
        var ticketType = TicketType.BASIC;
        var genre = Genre.MYSTERY;

        var user = new User("Vasya", "Pupkin", 18);
        var cinemaTicket = new CinemaTicket(id, user, rawPrice, filmName, ticketType, genre);
        var statistics = new TicketsStatistic(List.of(cinemaTicket));

        var expectedDiscount = BigDecimal.valueOf(0.1);
        var expectedPrice = rawPrice.multiply(BigDecimal.ONE.subtract(expectedDiscount));

        assertThat(cinemaTicket.getDiscountPercent())
                .as("Discount expected.")
                .isEqualTo(expectedDiscount);

        assertThat(statistics.calculateTotalPrice())
                .as("Discount expected.")
                .isEqualTo(expectedPrice);

        assertThat(statistics.toString())
                .as("Must be one line with correct data")
                .containsSequence("CinemaTicket")
                .containsSequence("id=" + id)
                .containsSequence("name='" + user.getName())
                .containsSequence("surname='" + user.getSurname())
                .containsSequence("age=" + user.getAge())
                .containsSequence("rawPrice=" + rawPrice)
                .containsSequence("filmName='" + filmName)
                .containsSequence("genre=" + genre)
                .containsSequence("ticketType=" + ticketType);
    }

    @Test
    void test_statistics_with_one_cinema_ticket_with_genre_discount_and_vip_ticket_type() {
        var id = 0;
        var filmName = "TestFilm";
        var rawPrice = BigDecimal.valueOf(1000);
        var ticketType = TicketType.VIP;
        var genre = Genre.MYSTERY;

        var user = new User("Vasya", "Pupkin", 18);
        var cinemaTicket = new CinemaTicket(id, user, rawPrice, filmName, ticketType, genre);
        var statistics = new TicketsStatistic(List.of(cinemaTicket));

        var expectedDiscount = BigDecimal.valueOf(0.1);
        var expectedPrice = rawPrice
                .multiply(BigDecimal.ONE.subtract(expectedDiscount))
                .multiply(ticketType.getCostMultiplier());

        assertThat(cinemaTicket.getDiscountPercent())
                .as("Discount expected.")
                .isEqualTo(expectedDiscount);

        assertThat(statistics.calculateTotalPrice())
                .as("Discount expected. Vip ticket costs more than common. ")
                .isEqualTo(expectedPrice);

        assertThat(statistics.toString())
                .as("Must be one line with correct data")
                .containsSequence("CinemaTicket")
                .containsSequence("id=" + id)
                .containsSequence("name='" + user.getName())
                .containsSequence("surname='" + user.getSurname())
                .containsSequence("age=" + user.getAge())
                .containsSequence("rawPrice=" + rawPrice)
                .containsSequence("filmName='" + filmName)
                .containsSequence("genre=" + genre)
                .containsSequence("ticketType=" + ticketType);
    }

    @Test
    void test_statistics_with_one_theater_ticket() {
        var id = 0;
        var playName = "TestPlay";
        var rawPrice = BigDecimal.valueOf(500);
        var playStyle = PlayStyle.MODERN;

        var user = new User("Vasya", "Pupkin", 18);
        var cinemaTicket = new TheaterTicket(id, user, rawPrice, playName, playStyle);

        var statistics = new TicketsStatistic(List.of(cinemaTicket));

        assertThat(cinemaTicket.getDiscountPercent())
                .as("No discount expected.")
                .isEqualTo(BigDecimal.ZERO);

        assertThat(statistics.calculateTotalPrice())
                .as("Total price must be equal to rawPrice. No discount expected.")
                .isEqualTo(rawPrice);

        assertThat(statistics.toString())
                .as("Must be one line with correct data")
                .containsSequence("TheaterTicket")
                .containsSequence("id=" + id)
                .containsSequence("name='" + user.getName())
                .containsSequence("surname='" + user.getSurname())
                .containsSequence("age=" + user.getAge())
                .containsSequence("rawPrice=" + rawPrice)
                .containsSequence("playTitle='" + playName)
                .containsSequence("playStyle=" + playStyle);
    }

    @Test
    void test_statistics_with_one_theater_ticket_with_retirement_discount() {
        var id = 0;
        var playName = "TestPlay";
        var rawPrice = BigDecimal.valueOf(500);
        var playStyle = PlayStyle.MODERN;
        var user = new User("Vasya", "Pensioner", 99);
        var cinemaTicket = new TheaterTicket(id, user, rawPrice, playName, playStyle);
        var statistics = new TicketsStatistic(List.of(cinemaTicket));

        var expectedDiscount = BigDecimal.valueOf(0.5);
        var expectedPrice = rawPrice.multiply(
                BigDecimal.ONE.subtract(cinemaTicket.getDiscountPercent()));

        assertThat(cinemaTicket.getDiscountPercent())
                .as("Retirement discount expected.")
                .isEqualTo(expectedDiscount);

        assertThat(statistics.calculateTotalPrice())
                .as("Total price must be equal to rawPrice. No discount expected.")
                .isEqualTo(expectedPrice);

        assertThat(statistics.toString())
                .as("Must be one line with correct data")
                .containsSequence("TheaterTicket")
                .containsSequence("id=" + id)
                .containsSequence("name='" + user.getName())
                .containsSequence("surname='" + user.getSurname())
                .containsSequence("age=" + user.getAge())
                .containsSequence("rawPrice=" + rawPrice)
                .containsSequence("playTitle='" + playName)
                .containsSequence("playStyle=" + playStyle);
    }

    @Test
    void test_statistics_total_price_different_ticket_types(){
        var id = 0;
        var filmName = "TestFilm";
        var rawPrice = BigDecimal.valueOf(500);
        var ticketType = TicketType.BASIC;
        var genre = Genre.ACTION;

        var playTitle = "TestPlay";
        var playStyle = PlayStyle.MODERN;

        var user = new User("Vasya", "Pupkin", 18);
        var cinemaTicket = new CinemaTicket(id++, user, rawPrice, filmName, ticketType, genre);

        var anotherTicket = new TheaterTicket(id, user, rawPrice, playTitle, playStyle);

        var statistics = new TicketsStatistic(List.of(cinemaTicket, anotherTicket));

        assertThat(statistics.calculateTotalPrice())
                .as("Should sum correctly")
                .isEqualTo(rawPrice.multiply(BigDecimal.valueOf(2)));
    }

    @Test
    void test_statistics_sorting_order(){
        var id = 0;
        var filmName = "TestFilm";
        var rawPrice = BigDecimal.valueOf(500);
        var ticketType = TicketType.BASIC;
        var genre = Genre.ACTION;

        var playTitle = "TestPlay";
        var playStyle = PlayStyle.MODERN;

        var user1 = new User("Avraam", "Pavlov", 24);
        var user2 = new User("Daniel", "Gorockhov", 37);

        var cinemaTicket = new CinemaTicket(id++, user1, rawPrice, filmName, ticketType, genre);
        var theaterTicket =  new TheaterTicket(id, user2, rawPrice, playTitle, playStyle);

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
