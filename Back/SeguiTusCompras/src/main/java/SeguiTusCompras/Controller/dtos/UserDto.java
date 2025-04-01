package SeguiTusCompras.Controller.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class UserDto {
    private String name;
    private String password;
    private String role;

    public UserDto(String name, String password, String role) {}
}
