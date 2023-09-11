package ru.aston.oshchepkov_aa.task4.user;

import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.aston.oshchepkov_aa.task4.user.UserSql.*;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final JdbcConnectionPool connectionPool;

    public UserRepositoryImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<User> findAll() {
        var result = new ArrayList<User>();
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.createStatement()) {
                var hasResult = stmt.execute(SELECT_ALL_USERS);
                if (hasResult) {
                    var resultSet = stmt.getResultSet();
                    if (!resultSet.isBeforeFirst()) {
                        return result;
                    }
                    while (resultSet.next()) {
                        result.add(UserMapper.resultSetToUser(resultSet));
                    }
                    resultSet.close();
                }
                return result;
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            return result;
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(SELECT_USER_BY_ID)) {
                stmt.setInt(1, id);
                var hasResult = stmt.execute();
                if (hasResult) {
                    var resultSet = stmt.getResultSet();
                    if (!resultSet.isBeforeFirst()) {
                        return Optional.empty();
                    }
                    resultSet.next();
                    var result = Optional.of(UserMapper.resultSetToUser(resultSet));
                    resultSet.close();
                    return result;
                }
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(DELETE_USER_BY_ID)) {
                stmt.setInt(1, id);
                stmt.execute();
                var updated = stmt.getUpdateCount();
                if (updated > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            return false;
        }
        return false;
    }

    @Override
    public boolean create(User user) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(ADD_USER)) {
                stmt.setString(1, user.getFirstName());
                stmt.setString(2, user.getLastName());
                stmt.setString(3, user.getMiddleName());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, user.getEmail());
                stmt.execute();
                var updated = stmt.getUpdateCount();
                if (updated > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            return false;
        }
        return false;
    }

    @Override
    public Optional<User> update(User user) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(UPDATE_USER_BY_ID)) {
                stmt.setString(1, user.getFirstName());
                stmt.setString(2, user.getLastName());
                stmt.setString(3, user.getMiddleName());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, user.getEmail());
                stmt.setInt(6, user.getId());
                stmt.execute();
                if (stmt.getUpdateCount() > 0){
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.empty();
    }
}
