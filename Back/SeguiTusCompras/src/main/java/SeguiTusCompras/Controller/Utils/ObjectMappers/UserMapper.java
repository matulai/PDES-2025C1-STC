package SeguiTusCompras.Controller.Utils.ObjectMappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.Controller.dtos.SimpleProductDto;
import SeguiTusCompras.Controller.dtos.SimpleUserDto;
import SeguiTusCompras.Controller.dtos.UserDto;
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

    public static UserDto convertToDto(User clientWithNewFavorite) {
        Set<Product> favorites = clientWithNewFavorite.getFavorites();
        List<Product> purchases = clientWithNewFavorite.getPurchases();
        Set<Qualification> qualifications = clientWithNewFavorite.getQualifications();
        List<SimpleProductDto> favDtos = ProductMapper.convertToListSimpleDto(favorites);
        List<SimpleProductDto> purchaseDtos = ProductMapper.convertToListSimpleDto(purchases);
        List<QualificationDto> qualificationDtos = QualificationMapper.convertListToDto(qualifications);
        UserDto userDto = new UserDto(clientWithNewFavorite.getName(), clientWithNewFavorite.getRole().name(), 
                                        favDtos, purchaseDtos, qualificationDtos);
        return userDto;
    }
}
