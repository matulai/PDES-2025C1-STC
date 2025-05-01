package SeguiTusCompras.Controller.Utils.Validators;

import lombok.Getter;

@Getter
public enum ValidatorsErrors {
    INVALID_PASSWORD("invalid password format"),
    INVALID_ROLE("Invalid Role");

    private final String message;

    ValidatorsErrors(String message) {
        this.message = message;
    }

}
