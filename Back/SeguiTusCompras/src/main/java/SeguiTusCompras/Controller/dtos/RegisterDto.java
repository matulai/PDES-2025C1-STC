package SeguiTusCompras.Controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
        message = "Wrong password format"
    )
    private String password;
    @NotBlank
    @Pattern(regexp = "(?i)^(Client|Admin)$", message = "Invalid Role")
    private String role;
}
