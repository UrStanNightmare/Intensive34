package ru.aston.oshchepkov_aa.task4;

import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.order.OrderRepositoryImpl;
import ru.aston.oshchepkov_aa.task4.order.OrderService;
import ru.aston.oshchepkov_aa.task4.order.OrderServiceImpl;
import ru.aston.oshchepkov_aa.task4.user.UserRepositoryImpl;
import ru.aston.oshchepkov_aa.task4.user.UserService;
import ru.aston.oshchepkov_aa.task4.user.UserServiceImpl;
import ru.aston.oshchepkov_aa.task4.utils.PropertyReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DbService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(DbService.class);
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String INIT_USERS_SCRIPT_PATH = "/sql/initUsers.sql";
    private static final String INIT_ORDERS_SCRIPT_PATH = "/sql/initOrders.sql";
    private static final String INIT_USER_ORDER_SCRIPT_PATH = "/sql/initUserOrder.sql";
    private static DbService service;

    private final JdbcConnectionPool cp;
    private final UserService userService;
    private final OrderService orderService;

    private DbService() {
        log.info("Creating db service");

        var pr = PropertyReader.getReader();
        var url = pr.getString(URL_KEY);
        var user = pr.getString(USERNAME_KEY);
        var password = pr.getString(PASSWORD_KEY);

        cp = JdbcConnectionPool.create(url, user, password);

        initDb();
        log.info("Creation done. Ready to use.");

        this.userService = new UserServiceImpl(new UserRepositoryImpl(cp));
        this.orderService = new OrderServiceImpl(new OrderRepositoryImpl(cp));
    }

    public static DbService getService() {
        if (null == service) {
            service = new DbService();
        }
        return service;
    }

    @Override
    public void close() throws Exception {
        cp.dispose();
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    private void initDb() {
        log.info("Init db started.");

        try (var c = cp.getConnection()) {
            initTable(c, INIT_USERS_SCRIPT_PATH);
            initTable(c, INIT_ORDERS_SCRIPT_PATH);
            initTable(c, INIT_USER_ORDER_SCRIPT_PATH);

        } catch (SQLException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        log.info("Init done.");
    }

    private void initTable(Connection connection, String filePath) throws SQLException, URISyntaxException, IOException {
        var initString = Files.readString(Paths.get(Objects.requireNonNull(getClass()
                .getResource(filePath)).toURI()));

        log.info("InitScript:{}{}", System.lineSeparator(), initString);

        try (var s = connection.createStatement()) {
            s.executeUpdate(initString);
        }
    }
}
