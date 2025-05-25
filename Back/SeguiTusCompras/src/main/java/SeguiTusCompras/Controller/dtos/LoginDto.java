package SeguiTusCompras.Controller.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;;

@Data
public class LoginDto {
    @NotBlank(message = "name can't be empty")
    private String name;
    @NotBlank(message = "password can't be empty")
    private String password;
}
