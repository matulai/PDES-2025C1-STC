package SeguiTusCompras.Error;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    INVALID_PASSWORD("invalid password format"),
    INVALID_ROLE("Invalid Role"),
    ALREADY_REGISTERED("Someone else has chosen that name"),
    USER_NOT_FOUND("User not found"),
    PRODUCT_DOES_NOT_EXIST("Product does not exist"),
    INVALID_PASSWORD_OR_USERNAME("password or user name invalid"),
    INVALID_SCORE("score must be beetween 1 and 5");
    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}
