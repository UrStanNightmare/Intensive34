package ru.aston.oshchepkov_aa.task4.order;

import java.util.Objects;
import java.util.StringJoiner;

public class Order {
    private int id;
    private String description;
    private int amount;

    public Order() {
    }

    public Order(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    public Order(int id, String description, int amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && amount == order.amount && Objects.equals(description, order.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("description='" + description + "'")
                .add("amount=" + amount)
                .toString();
    }
}
