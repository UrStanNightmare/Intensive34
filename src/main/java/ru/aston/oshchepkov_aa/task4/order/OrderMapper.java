package ru.aston.oshchepkov_aa.task4.order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {
    private OrderMapper() {}

    public static Order resultSetToOrder(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var description = rs.getString("description");
        var amount = rs.getInt("amount");

        return new Order(id, description, amount);
    }
}
