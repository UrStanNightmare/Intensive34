package ru.aston.oshchepkov_aa.task4.user;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User findUserById(int id);
    boolean deleteUserById(int id);
    boolean createUser(User user);
    User updateUser(User user);
}
