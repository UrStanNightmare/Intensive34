package ru.aston.oshchepkov_aa.task4.user;

import ru.aston.oshchepkov_aa.task4.cli.exception.ValidationException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteUserById(int id) {
        return userRepository.deleteById(id);
    }

    @Override
    public boolean createUser(User user) {
        if (validate(user)){
            return userRepository.create(user);
        }
        throw new ValidationException("Fields not valid!");
    }

    @Override
    public User updateUser(User user) {
        if (validate(user)){
            return userRepository.update(user).orElse(null);
        }
        throw new ValidationException("Fields not valid!");
    }

    private boolean validate(User user){
        var name = user.getFirstName();
        if (null == name || name.length() > 50){
            return false;
        }
        var lastName = user.getLastName();
        if (null == lastName || lastName.length() > 50){
            return false;
        }
        var middleName = user.getMiddleName();
        if (middleName != null && middleName.length() > 50){
            return false;
        }
        var phoneNumber = user.getPhoneNumber();
        if (!phoneNumber.matches("^\\+?[1-9][0-9]{6,11}$")){
            return false;
        }
        var email = user.getEmail();
        if (!email.matches("^\\S+@\\S+\\.\\S+$")){
            return false;
        }
        return true;
    }
}
