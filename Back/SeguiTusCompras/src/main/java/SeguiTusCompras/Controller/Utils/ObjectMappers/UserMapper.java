package SeguiTusCompras.Controller.Utils.ObjectMappers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import SeguiTusCompras.Controller.dtos.AdminDto;
import SeguiTusCompras.Controller.dtos.ClientDto;
import SeguiTusCompras.Controller.dtos.ProductDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.User;

public class UserMapper {
    public static UserDto convertToDto(User newUser) {
        if (newUser.getClass() == Client.class){
            Client client = (Client) newUser;
            Optional<Set<ProductDto>> productDtos = getProductDtos(client);
            return new ClientDto(client.getName(), productDtos);
        }
        return new AdminDto(newUser.getName());
    }

    private  static Optional<Set<ProductDto>> getProductDtos(Client client) {
        Set <ProductDto> productDtos = new HashSet<>();
        for (Product product : client.getFavorites()){
            productDtos.add(new ProductDto(product.getName()));
        }
        return Optional.of(productDtos);
    }
}
