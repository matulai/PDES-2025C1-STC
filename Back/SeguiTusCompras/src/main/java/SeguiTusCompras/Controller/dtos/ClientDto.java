package SeguiTusCompras.Controller.dtos;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
@Getter
public class ClientDto extends UserDto{
    private Optional<Set<ProductDto>> favs;
    private Optional<ArrayList<PurchaseDto>> purchases;
    public ClientDto(String name, Optional<Set<ProductDto>> productDtos, Optional<ArrayList<PurchaseDto>> purchaseDtos) {
        super(name);
        this.favs = productDtos;
        this.purchases = purchaseDtos;
    }
}
