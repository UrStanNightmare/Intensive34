package ru.aston.oshchepkov_aa.task4.order;

public class OrderSql {
    public static final String SELECT_ALL_ORDERS = "SELECT * FROM orders ORDER BY id;";
    public static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?;";
    public static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?;";
    public static final String ADD_ORDER =
            "INSERT INTO orders (DESCRIPTION, AMOUNT) " +
                    "VALUES (?, ?);";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE orders " +
            "SET description = ?, amount = ? " +
            "WHERE id = ?;";
    public static final String ALL_ORDERS_BY_USER_ID = "SELECT o.id, o.description, o.amount " +
            "FROM user_order as uo " +
            "LEFT JOIN orders AS o ON uo.order_id = o.id " +
            "WHERE uo.user_id = ? " +
            "ORDER BY o.id;";

    public static final String ASSIGN_ORDER_TO_USER_BY_IDS = "INSERT INTO user_order (user_id, order_id) VALUES (?, ?)";
    public static final String DELETE_ORDER_USER_BY_IDS = "DELETE FROM user_order WHERE user_id = ? AND order_id = ?;";

    private OrderSql() {
    }
}
