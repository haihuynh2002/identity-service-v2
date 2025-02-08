package com.devteria.identity_service.exception;

public enum ErrorCode {
    INVALID_KEY(1001, "Invalid message key"),
    USER_EXISTS(1002, "User exists"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1004, "Username must be at least 8 characters"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
