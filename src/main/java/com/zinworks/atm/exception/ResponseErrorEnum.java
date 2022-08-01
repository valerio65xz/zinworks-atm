package com.zinworks.atm.exception;

public enum ResponseErrorEnum {

    NOT_ENOUGH_BALANCE("The balance available is less than the amount requested", 400),
    NOT_VALID_AMOUNT("The amount requested is not valid", 400),
    BANKNOTES_TERMINATED("There aren't enough banknotes to fullfil the request", 400),
    PIN_NOT_VALID("The pin is not valid", 401),
    USER_NOT_FOUND("User not found", 404);

    private final String message;
    private final int httpStatus;

    ResponseErrorEnum(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
