package SeguiTusCompras.Controller.dtos;

import java.util.Optional;
import java.util.Set;

import lombok.Getter;

@Getter
public class ClientDto extends UserDto{
    private final Optional<Set<ProductDto>> favs;
    public ClientDto(String name, Optional<Set<ProductDto>> productDtos) {
        super(name);
        this.favs = productDtos;
    }
}
