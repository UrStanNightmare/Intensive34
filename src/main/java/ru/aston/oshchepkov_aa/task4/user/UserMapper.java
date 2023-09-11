package ru.aston.oshchepkov_aa.task4.user;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    private UserMapper() {}

    public static User resultSetToUser(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var firstName = rs.getString("first_name");
        var lastName = rs.getString("last_name");
        var middleName = rs.getString("middle_name");
        var phoneNumber = rs.getString("phone_number");
        var email = rs.getString("email");

        return new User(id, firstName, lastName, middleName, phoneNumber, email);
    }
}
