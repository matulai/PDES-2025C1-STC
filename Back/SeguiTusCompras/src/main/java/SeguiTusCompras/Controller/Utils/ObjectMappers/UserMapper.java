package SeguiTusCompras.Controller.Utils.ObjectMappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import SeguiTusCompras.Controller.dtos.AdminDto;
import SeguiTusCompras.Controller.dtos.ClientDto;
import SeguiTusCompras.Controller.dtos.ProductDto;
import SeguiTusCompras.Controller.dtos.PurchaseDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Purchase;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.User;

public class UserMapper {
    public static UserDto convertToDto(User newUser) {
        if (newUser.getClass() == Client.class){
            Client client = (Client) newUser;
            Optional<Set<ProductDto>> productDtos = getProductDtos(client);
            Optional<ArrayList<PurchaseDto>> purchaseDtos = getPurchasesDtos(client);
            return new ClientDto(client.getName(), productDtos, purchaseDtos);
        }
        return new AdminDto(newUser.getName());
    }

    private  static Optional<Set<ProductDto>> getProductDtos(Client client) {
        Set <ProductDto> productDtos = new HashSet<>();
        for (Product product : client.getFavs()){
            productDtos.add(new ProductDto(product.getName()));
        }
        return Optional.of(productDtos);
    }

    private  static Optional<ArrayList<PurchaseDto>> getPurchasesDtos(Client client) {
        ArrayList <PurchaseDto> purchaseDtos = new ArrayList<>();
        for (Purchase purchase : client.getPurchases()){
            purchaseDtos.add(new PurchaseDto(client.getName(), new ProductDto(purchase.getProduct().getName()), purchase.getNumberOfUnits()));
        }
        return Optional.of(purchaseDtos);
    }
}
