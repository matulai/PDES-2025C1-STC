package SeguiTusCompras.Controller.dtos;

import lombok.Getter;

@Getter
public class ClientDto extends UserDto{

    public ClientDto(String name) {
        super(name);
    }
}
