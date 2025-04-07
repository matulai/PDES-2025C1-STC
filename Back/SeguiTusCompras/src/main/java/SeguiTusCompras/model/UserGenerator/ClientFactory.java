package SeguiTusCompras.model.UserGenerator;

import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.User;

public class ClientFactory implements IUserFactory {

    @Override
    public User createUser(String name) {
        return new Client(name);
    }
}
