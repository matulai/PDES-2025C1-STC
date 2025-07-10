package SeguiTusCompras.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "name can't be empty")
    private String name;
    @NotBlank(message = "password can't be empty")
    private String password;
}
