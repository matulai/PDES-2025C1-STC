package SeguiTusCompras.Controller.Utils.ObjectMappers;

import SeguiTusCompras.Controller.dtos.AdminDto;
import SeguiTusCompras.Controller.dtos.ClientDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.User;

public class UserMapper {
    public static UserDto convertToDto(User newUser) {
        if (newUser.getClass() == Client.class){
            Client client = (Client) newUser;
            return new ClientDto(client.getName(), client.getFavs());
        }
        return new AdminDto(newUser.getName());
    }
}
