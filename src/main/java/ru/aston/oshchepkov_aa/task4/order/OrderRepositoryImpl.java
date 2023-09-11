package ru.aston.oshchepkov_aa.task4.order;

import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.aston.oshchepkov_aa.task4.order.OrderSql.*;

public class OrderRepositoryImpl implements OrderRepository {
    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    private final JdbcConnectionPool connectionPool;

    public OrderRepositoryImpl(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public List<Order> findAll() {
        var result = new ArrayList<Order>();
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.createStatement()) {
                var hasResult = stmt.execute(SELECT_ALL_ORDERS);
                if (hasResult) {
                    var resultSet = stmt.getResultSet();
                    if (!resultSet.isBeforeFirst()) {
                        return result;
                    }
                    while (resultSet.next()) {
                        result.add(OrderMapper.resultSetToOrder(resultSet));
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
    public Optional<Order> findById(int id) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(SELECT_ORDER_BY_ID)) {
                stmt.setInt(1, id);
                var hasResult = stmt.execute();
                if (hasResult) {
                    var resultSet = stmt.getResultSet();
                    if (!resultSet.isBeforeFirst()) {
                        return Optional.empty();
                    }
                    resultSet.next();
                    var result = Optional.of(OrderMapper.resultSetToOrder(resultSet));
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
            try (var stmt = c.prepareStatement(DELETE_ORDER_BY_ID)) {
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
    public boolean create(Order order) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(ADD_ORDER)) {
                stmt.setString(1, order.getDescription());
                stmt.setInt(2, order.getAmount());
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
    public Optional<Order> update(Order order) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(UPDATE_ORDER_BY_ID)) {
                stmt.setString(1, order.getDescription());
                stmt.setInt(2, order.getAmount());
                stmt.setInt(3, order.getId());
                stmt.execute();
                if (stmt.getUpdateCount() > 0) {
                    return Optional.of(order);
                }
            }
        } catch (SQLException e) {
            log.error("{}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        var result = new ArrayList<Order>();
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(ALL_ORDERS_BY_USER_ID)) {
                stmt.setInt(1, userId);
                var hasResult = stmt.execute();
                if (hasResult) {
                    var resultSet = stmt.getResultSet();
                    if (!resultSet.isBeforeFirst()) {
                        return result;
                    }
                    while (resultSet.next()) {
                        result.add(OrderMapper.resultSetToOrder(resultSet));
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
    public boolean assignOrderToUserByIds(int userId, int orderId) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(ASSIGN_ORDER_TO_USER_BY_IDS)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, orderId);
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
    public boolean deleteOrderUserPairByIds(int userId, int orderId) {
        try (var c = connectionPool.getConnection()) {
            try (var stmt = c.prepareStatement(DELETE_ORDER_USER_BY_IDS)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, orderId);
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
}
