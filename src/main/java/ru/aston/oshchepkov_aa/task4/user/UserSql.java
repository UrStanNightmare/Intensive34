package ru.aston.oshchepkov_aa.task4.user;

public class UserSql {
    public static final String SELECT_ALL_USERS = "SELECT * FROM users ORDER BY id;";
    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?;";
    public static final String ADD_USER =
            "INSERT INTO users (FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL) " +
            "VALUES (?, ?, ?, ?, ?);";
    public static final String UPDATE_USER_BY_ID = "UPDATE users " +
            "SET first_name = ?, last_name = ?, middle_name = ?, phone_number = ?, email = ? " +
            "WHERE id = ?;";

    private UserSql() {}
}
