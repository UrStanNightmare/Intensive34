CREATE TABLE IF NOT EXISTS user_order(
    user_id int8 NOT NULL,
    order_id int8 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES PUBLIC.users(id),
    FOREIGN KEY (order_id) REFERENCES PUBLIC.orders(id),
    PRIMARY KEY (user_id, order_id)

)