package SeguiTusCompras.Controller.Utils.Validators;

import SeguiTusCompras.Controller.dtos.RegisterDto;
import SeguiTusCompras.model.user.Role;

import java.util.Arrays;
import java.util.regex.Pattern;

public class AuthValidator {
    private static AuthValidator instance;
    private static final String passwordRegex= "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";


    public static AuthValidator getInstance() {
        if (instance == null){
            instance = new AuthValidator();
        }
        return instance;
    }

    public void ValidateRegister(RegisterDto register) {
        itsAnInvalidRole(register.getRole());
        itsAnInValidPassword(register.getPassword());
    }

    private void itsAnInValidPassword(String password) {
        if(!Pattern.matches(passwordRegex, password)) {
            throw new IllegalArgumentException(ValidatorsErrors.INVALID_PASSWORD.getMessage());
        }
    }

    private void itsAnInvalidRole(String userRole) {
        if (Arrays.stream(Role.values()).noneMatch(role -> role.name().equalsIgnoreCase(userRole))){;
            throw new IllegalArgumentException(ValidatorsErrors.INVALID_ROLE.getMessage());
        }
    }
}
