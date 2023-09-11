package ru.aston.oshchepkov_aa.task4;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.aston.oshchepkov_aa.task4.cli.exception.ValidationException;
import ru.aston.oshchepkov_aa.task4.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DbApplicationTest {
    private final DbService dbService = DbService.getService();
    private static final List<User> createdUsers = new ArrayList<>();
    private static final List<ru.aston.oshchepkov_aa.task4.order.Order> createdOrders = new ArrayList<>();

    @Test
    @Order(1)
    void test_get_users_from_empty_db() {
        List<User> allUsers = dbService.getUserService().getAll();

        assertThat(allUsers)
                .as("List must be empty")
                .isEmpty();
    }

    @Test
    @Order(2)
    void test_get_orders_from_empty_db() {
        List<ru.aston.oshchepkov_aa.task4.order.Order> allUsers = dbService.getOrderService().getAll();

        assertThat(allUsers)
                .as("List must be empty")
                .isEmpty();
    }

    @Test
    @Order(3)
    void test_create_user_incorrect() {
        var wrongUser = new User(null, null, null, null, null);

        assertThatThrownBy(() -> {
            dbService.getUserService().createUser(wrongUser);
        }).as("Must be validation exception")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @Order(4)
    void test_create_order_incorrect() {
        var wrongOrder = new ru.aston.oshchepkov_aa.task4.order.Order(null, -1);

        assertThatThrownBy(() -> {
            dbService.getOrderService().createOrder(wrongOrder);
        }).as("Must be validation exception")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @Order(5)
    void test_create_user_correct() {
        var user = createDefaultUser();

        boolean isCreated = dbService.getUserService().createUser(user);

        assertThat(isCreated)
                .as("Must be successful")
                .isTrue();

        var allUsersList = dbService.getUserService().getAll();
        assertThat(allUsersList)
                .as("Must contain 1 user")
                .hasSize(1);
        var userInDb = allUsersList.get(0);

        user.setId(userInDb.getId());
        assertThat(userInDb)
                .as("Must be the same")
                .isEqualTo(user);

        createdUsers.add(user);
    }

    @Test
    @Order(6)
    void test_create_order_correct() {
        var order = createDefaultOrder();

        boolean isCreated = dbService.getOrderService().createOrder(order);

        assertThat(isCreated)
                .as("Must be successful")
                .isTrue();

        var allOrdersList = dbService.getOrderService().getAll();
        assertThat(allOrdersList)
                .as("Must contain only 1 order")
                .hasSize(1);
        var orderInDb = allOrdersList.get(0);

        order.setId(orderInDb.getId());
        assertThat(orderInDb)
                .as("Must be the same")
                .isEqualTo(order);

        createdOrders.add(order);
    }

    @Test
    @Order(7)
    void test_assign_orders_to_user() {
        var newOrder = createDefaultOrder();
        newOrder.setDescription("Arma 3");
        newOrder.setAmount(2);


        boolean order = dbService.getOrderService().createOrder(newOrder);
        assertThat(order)
                .as("Must be created successfully.")
                .isTrue();

        var allOrdersList = dbService.getOrderService().getAll();
        assertThat(allOrdersList)
                .as("Must be 2 elements")
                .hasSize(2);

        var lastOrderInDb = allOrdersList.get(1);
        newOrder.setId(lastOrderInDb.getId());

        assertThat(lastOrderInDb)
                .as("Must be the same")
                .isEqualTo(newOrder);

        createdOrders.add(newOrder);

        var userId = createdUsers.get(0).getId();

        for (var o : createdOrders) {
            var result = dbService.getOrderService().assignOrderToUserByIds(userId, o.getId());
            assertThat(result)
                    .isTrue();
        }

        var specifiedUserOrders = dbService.getOrderService().getUserOrders(userId);
        assertThat(specifiedUserOrders)
                .as("Must be the same as saved locally")
                .hasSize(createdOrders.size())
                .hasSameElementsAs(createdOrders);
    }

    @Test
    @Order(8)
    void test_un_assign_user_orders() {
        var userId = createdUsers.get(0).getId();

        for (var o : createdOrders) {
            var result = dbService.getOrderService().deleteOrderUserPairByIds(userId, o.getId());
            assertThat(result)
                    .isTrue();
        }

        var ordersList = dbService.getOrderService().getUserOrders(userId);
        assertThat(ordersList)
                .as("All orders must be deleted.")
                .isEmpty();
    }

    @Test
    @Order(9)
    void test_edit_user_correct() {
        var user = createdUsers.get(0);
        user.setMiddleName(null);

        var userInDb = dbService.getUserService().updateUser(user);
        assertThat(userInDb)
                .as("Must be the same")
                .isEqualTo(user);
    }

    @Test
    @Order(10)
    void test_edit_order_correct() {
        var order = createdOrders.get(1);
        order.setDescription("Divinity: Original Sin 2");

        var orderInDb = dbService.getOrderService().updateOrder(order);
        assertThat(orderInDb)
                .as("Must be the same")
                .isEqualTo(order);
    }

    @Test
    @Order(11)
    void test_delete_orders() {
        for (var o : createdOrders) {
            var result = dbService.getOrderService().deleteOrderById(o.getId());
            assertThat(result)
                    .isTrue();
        }

        var ordersList = dbService.getOrderService().getAll();
        assertThat(ordersList)
                .as("No orders left total")
                .isEmpty();

        createdOrders.clear();
    }

    @Test
    void test_delete_users() {
        for (var u: createdUsers){
            var result = dbService.getUserService().deleteUserById(u.getId());
            assertThat(result)
                    .isTrue();
        }

        var usersList = dbService.getUserService().getAll();
        assertThat(usersList)
                .as("No orders left total")
                .isEmpty();

        createdUsers.clear();
    }

    private User createDefaultUser() {
        return new User("Vasya",
                "Pupkin",
                "Gennadievich",
                "+78005553535",
                "gde@dengy.vzyat");
    }

    private ru.aston.oshchepkov_aa.task4.order.Order createDefaultOrder() {
        return new ru.aston.oshchepkov_aa.task4.order.Order("Baldur's gate 3", 1);
    }
}