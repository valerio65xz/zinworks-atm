package com.zinworks.atm.exception;

public enum ResponseErrorEnum {

    NOT_ENOUGH_AMOUNT("001", "The balance available is less than the amount requested", 400),
    NOT_VALID_AMOUNT("002", "The amount requested is not valid", 400),
    BANKNOTES_TERMINATED("003", "There aren't enough banknotes to fullfil the request", 400),
    PIN_NOT_VALID("004", "The pin is not valid", 401),
    USER_NOT_FOUND("005", "User not found", 404);

    private final String code;
    private final String message;
    private final int httpStatus;

    ResponseErrorEnum(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
