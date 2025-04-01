package SeguiTusCompras.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ClientDto extends UserDto{

    public ClientDto(String name, String password, String role) {
        super(name, password, role);
    }
}
