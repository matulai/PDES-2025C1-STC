package SeguiTusCompras.Controller.Utils.ObjectMappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import SeguiTusCompras.Controller.dtos.*;
import SeguiTusCompras.model.PurchaseRecipe;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.user.User;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class UserMapper {

    public static SimpleUserDto convertToSimpleDto(User user) {
        SimpleUserDto userDto = new SimpleUserDto(user.getName(), user.getRole().name());
        return userDto;
    }

    public static List<SimpleUserDto> convertListToSimpleDto(List<User> clients) {
        List<SimpleUserDto> clientsDtos = new ArrayList<>();
        for (User client : clients){
                clientsDtos.add(convertToSimpleDto(client));
        }
        return clientsDtos;
    }

    public static List<SimpleUserDto> convertPagedListToSimpleDto(Page<User> clients) {
    return clients.getContent().stream()
                  .map(UserMapper::convertToSimpleDto)
                  .collect(Collectors.toList());
    }

    public static UserDto convertToDto(User clientWithNewFavorite) {
        Set<Product> favorites = clientWithNewFavorite.getFavorites();
        List<PurchaseRecipe> purchases = clientWithNewFavorite.getPurchases();
        Set<Qualification> qualifications = clientWithNewFavorite.getQualifications();
        List<Product> cartProducts = clientWithNewFavorite.getCart();
        List<SimpleProductDto> favDtos = ProductMapper.convertToListSimpleDto(favorites);
        List<PurchaseRecipeDto> purchaseRecipeDtos = ProductMapper.convertToListPurchaseRecipeDto(purchases);
        List<QualificationDto> qualificationDtos = QualificationMapper.convertListToDto(qualifications);
        List<ProductDto> cartProductDtos = ProductMapper.convertListToDto(cartProducts);
        UserDto userDto = new UserDto(clientWithNewFavorite.getName(), clientWithNewFavorite.getRole().name(), 
                                        favDtos, purchaseRecipeDtos, qualificationDtos, cartProductDtos);
        return userDto;
    }
}
