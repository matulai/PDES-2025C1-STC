package SeguiTusCompras.model.UserGenerator;

import SeguiTusCompras.model.user.Admin;
import SeguiTusCompras.model.user.User;

public class AdminFactory implements IUserFactory {
    @Override
    public User createUser(String name) {
        return new Admin(name);
    }
}
