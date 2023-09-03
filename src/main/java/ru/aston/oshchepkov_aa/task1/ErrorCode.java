package ru.aston.oshchepkov_aa.task1;

public enum ErrorCode {
    UNDEFINED(0, "ERROR CODE NOT SPECIFIED"),
    NEGATIVE_COST(1, "Price must be greater than zero."),
    INCORRECT_DISCOUNT(2, "Discount must be greater than zero and lesser than 1"),
    USER_NOT_SPECIFIED(3, "User must not be null")
    ;

    private final int code;
    private final String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
