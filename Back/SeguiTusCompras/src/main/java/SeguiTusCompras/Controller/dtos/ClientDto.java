package SeguiTusCompras.Controller.dtos;

import java.util.Set;

import SeguiTusCompras.model.Product;
import lombok.Getter;

@Getter
public class ClientDto extends UserDto{

    public ClientDto(String name, Set<Product> set) {
        super(name);
    }
}
