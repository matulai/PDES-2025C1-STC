package SeguiTusCompras.Controller.Utils.ObjectMappers;
import java.util.ArrayList;
import java.util.List;

import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.model.user.User;

public class UserMapper {
    private static UserMapper instance;

    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto(user.getName(), user.getRole().name());
        return userDto;
    }

    public static UserMapper getInstance() {
        if (instance == null){
            instance = new UserMapper();
        }
        return instance;
    }

    public static List<UserDto> convertListToDto(List<User> clients) {
        List<UserDto> clientsDtos = new ArrayList<>();
        for (User client : clients){
                clientsDtos.add(convertToDto(client));
        }
        return clientsDtos;
    }
}
