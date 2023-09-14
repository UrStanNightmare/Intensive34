package ru.aston.oshchepkov_aa.task5;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.aston.oshchepkov_aa.task4.cli.exception.ValidationException;
import ru.aston.oshchepkov_aa.task4.order.OrderRepository;
import ru.aston.oshchepkov_aa.task4.order.OrderService;
import ru.aston.oshchepkov_aa.task4.order.OrderServiceImpl;
import ru.aston.oshchepkov_aa.task4.user.User;
import ru.aston.oshchepkov_aa.task4.user.UserRepository;
import ru.aston.oshchepkov_aa.task4.user.UserService;
import ru.aston.oshchepkov_aa.task4.user.UserServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DbApplicationTest {
    private static final List<User> createdUsers = new ArrayList<>();
    private static final List<ru.aston.oshchepkov_aa.task4.order.Order> createdOrders = new ArrayList<>();

    private static UserService userService;
    private static OrderService orderService;

    private static UserRepository userRepository;
    private static int userSeq = 1;
    private static OrderRepository orderRepository;
    private static int orderSeq = 1;
    private static final Map<Integer, List<Integer>> userOrder = new HashMap<>();

    @BeforeAll
    static void setUp() {
        initializeUserRepositoryMock();
        userService = new UserServiceImpl(userRepository);

        initializeOrderRepositoryMock();
        orderService = new OrderServiceImpl(orderRepository);
    }

    private static void initializeUserRepositoryMock(){
        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAll()).thenAnswer(i ->
                createdUsers.stream()
                        .sorted(Comparator.comparing(User::getId))
                        .toList());
        Mockito.when(userRepository.findById(anyInt()))
                .thenAnswer(i -> {
                    int id = i.getArgument(0);
                    return createdUsers.stream()
                            .filter(u -> u.getId() == id)
                            .findFirst();
                });
        Mockito.when(userRepository.deleteById(anyInt()))
                .thenAnswer(i -> {
                    int id = i.getArgument(0);
                    return createdUsers.removeIf(u -> u.getId() == id);
                });
        Mockito.when(userRepository.create(any(User.class)))
                .thenAnswer(i -> {
                    User u = i.getArgument(0);
                    u.setId(userSeq++);
                    return createdUsers.add(u);
                });
        Mockito.when(userRepository.update(any(User.class)))
                .thenAnswer(i -> {
                    User u = i.getArgument(0);

                    var userInDb = createdUsers.stream()
                            .filter(user -> user.getId() == u.getId())
                            .findFirst();

                    if (userInDb.isPresent()){
                        var actU = userInDb.get();
                        actU.setFirstName(u.getFirstName());
                        actU.setLastName(u.getLastName());
                        actU.setMiddleName(u.getMiddleName());
                        actU.setEmail(u.getEmail());
                        actU.setPhoneNumber(u.getPhoneNumber());

                        return userInDb;
                    }else {
                        return Optional.empty();
                    }
                });
    }
    private static void initializeOrderRepositoryMock(){
        orderRepository = Mockito.mock(OrderRepository.class);
        Mockito.when(orderRepository.findAll()).thenAnswer(i ->
                createdOrders.stream()
                        .sorted(Comparator.comparing(ru.aston.oshchepkov_aa.task4.order.Order::getId))
                        .toList());
        Mockito.when(orderRepository.findById(anyInt()))
                .thenAnswer(i -> {
                    int id = i.getArgument(0);
                    return createdOrders.stream()
                            .filter(o -> o.getId() == id)
                            .findFirst();
                });
        Mockito.when(orderRepository.deleteById(anyInt()))
                .thenAnswer(i -> {
                    int id = i.getArgument(0);
                    return createdOrders.removeIf(o -> o.getId() == id);
                });
        Mockito.when(orderRepository.create(any(ru.aston.oshchepkov_aa.task4.order.Order.class)))
                .thenAnswer(i -> {
                    ru.aston.oshchepkov_aa.task4.order.Order o = i.getArgument(0);
                    o.setId(orderSeq++);
                    return createdOrders.add(o);
                });
        Mockito.when(orderRepository.update(any(ru.aston.oshchepkov_aa.task4.order.Order.class)))
                .thenAnswer(i -> {
                    ru.aston.oshchepkov_aa.task4.order.Order o = i.getArgument(0);

                    var orderInDb = createdOrders.stream()
                            .filter(order -> order.getId() == o.getId())
                            .findFirst();

                    if (orderInDb.isPresent()){
                        var actO = orderInDb.get();
                        actO.setDescription(o.getDescription());
                        actO.setAmount(o.getAmount());

                        return orderInDb;
                    }else {
                        return Optional.empty();
                    }
                });
        Mockito.when(orderRepository.getUserOrders(anyInt()))
                .thenAnswer(i -> {
                   int id = i.getArgument(0);
                   var orders = userOrder.getOrDefault(id, List.of());

                   if (orders.isEmpty()){
                       return orders;
                   }

                   var orderList = new ArrayList<ru.aston.oshchepkov_aa.task4.order.Order>(orders.size());
                   orders.forEach(o -> {
                       var order = orderRepository.findById(o);
                       order.ifPresent(orderList::add);
                   });

                   return orderList.stream()
                           .sorted(Comparator.comparing(ru.aston.oshchepkov_aa.task4.order.Order::getId))
                           .toList();
                });
        Mockito.when(orderRepository.assignOrderToUserByIds(anyInt(), anyInt()))
                .thenAnswer(i -> {
                   int userId = i.getArgument(0);
                   int orderId = i.getArgument(1);

                   var storedOrders = userOrder
                           .computeIfAbsent(userId, o -> new ArrayList<>(1));
                   storedOrders.add(orderId);

                   return true;
                });
        Mockito.when(orderRepository.deleteOrderUserPairByIds(anyInt(), anyInt()))
                .thenAnswer(i -> {
                    int userId = i.getArgument(0);
                    int orderId = i.getArgument(1);

                    var storedOrders = userOrder
                            .computeIfAbsent(userId, o -> new ArrayList<>(1));

                    if (storedOrders.isEmpty()){
                        return false;
                    }

                    storedOrders.remove((Integer) orderId);
                    return true;
                });
    }

    @Test
    @Order(1)
    void test_get_users_from_empty_db() {
        List<User> allUsers = userService.getAll();

        assertThat(allUsers)
                .as("List must be empty")
                .isEmpty();
    }

    @Test
    @Order(2)
    void test_get_orders_from_empty_db() {
        List<ru.aston.oshchepkov_aa.task4.order.Order> allUsers = orderService.getAll();

        assertThat(allUsers)
                .as("List must be empty")
                .isEmpty();
    }

    @Test
    @Order(3)
    void test_create_user_incorrect() {
        var wrongUser = new User(null, null, null, null, null);

        assertThatThrownBy(() -> {
            userService.createUser(wrongUser);
        }).as("Must be validation exception")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @Order(4)
    void test_create_order_incorrect() {
        var wrongOrder = new ru.aston.oshchepkov_aa.task4.order.Order(null, -1);

        assertThatThrownBy(() -> {
            orderService.createOrder(wrongOrder);
        }).as("Must be validation exception")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @Order(5)
    void test_create_user_correct() {
        var user = createDefaultUser();

        boolean isCreated = userService.createUser(user);

        assertThat(isCreated)
                .as("Must be successful")
                .isTrue();

        var allUsersList = userService.getAll();
        assertThat(allUsersList)
                .as("Must contain 1 user")
                .hasSize(1);
        var userInDb = allUsersList.get(0);

        user.setId(userInDb.getId());
        assertThat(userInDb)
                .as("Must be the same")
                .isEqualTo(user);
    }

    @Test
    @Order(6)
    void test_create_order_correct() {
        var order = createDefaultOrder();

        boolean isCreated = orderService.createOrder(order);

        assertThat(isCreated)
                .as("Must be successful")
                .isTrue();

        var allOrdersList = orderService.getAll();
        assertThat(allOrdersList)
                .as("Must contain only 1 order")
                .hasSize(1);
        var orderInDb = allOrdersList.get(0);

        order.setId(orderInDb.getId());
        assertThat(orderInDb)
                .as("Must be the same")
                .isEqualTo(order);
    }

    @Test
    @Order(7)
    void test_assign_orders_to_user() {
        var newOrder = createDefaultOrder();
        newOrder.setDescription("Arma 3");
        newOrder.setAmount(2);

        boolean order = orderService.createOrder(newOrder);
        assertThat(order)
                .as("Must be created successfully.")
                .isTrue();

        var allOrdersList = orderService.getAll();
        assertThat(allOrdersList)
                .as("Must be 2 elements")
                .hasSize(2);

        var lastOrderInDb = allOrdersList.get(1);
        newOrder.setId(lastOrderInDb.getId());

        assertThat(lastOrderInDb)
                .as("Must be the same")
                .isEqualTo(newOrder);

        var userId = createdUsers.get(0).getId();

        for (var o : createdOrders) {
            var result = orderService.assignOrderToUserByIds(userId, o.getId());
            assertThat(result)
                    .isTrue();
        }

        var specifiedUserOrders = orderService.getUserOrders(userId);
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
            var result = orderService.deleteOrderUserPairByIds(userId, o.getId());
            assertThat(result)
                    .isTrue();
        }

        var ordersList = orderService.getUserOrders(userId);
        assertThat(ordersList)
                .as("All orders must be deleted.")
                .isEmpty();
    }

    @Test
    @Order(9)
    void test_edit_user_correct() {
        var user = createdUsers.get(0);
        user.setMiddleName(null);

        var userInDb = userService.updateUser(user);
        assertThat(userInDb)
                .as("Must be the same")
                .isEqualTo(user);
    }

    @Test
    @Order(10)
    void test_edit_order_correct() {
        var order = createdOrders.get(1);
        order.setDescription("Divinity: Original Sin 2");

        var orderInDb = orderService.updateOrder(order);
        assertThat(orderInDb)
                .as("Must be the same")
                .isEqualTo(order);
    }

    @Test
    @Order(11)
    void test_delete_orders() {
        var deleteIds = createdOrders.stream()
                .map(ru.aston.oshchepkov_aa.task4.order.Order::getId)
                .toList();

        for (var id : deleteIds) {
            var result = orderService.deleteOrderById(id);
            assertThat(result)
                    .isTrue();
        }

        var ordersList = orderService.getAll();
        assertThat(ordersList)
                .as("No orders left total")
                .isEmpty();
    }

    @Test
    @Order(12)
    void test_delete_users() {
        var deleteIds = createdUsers.stream()
                .map(User::getId)
                .toList();

        for (var id: deleteIds){
            var result = userService.deleteUserById(id);
            assertThat(result)
                    .isTrue();
        }

        var usersList = userService.getAll();
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