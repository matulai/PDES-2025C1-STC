package SeguiTusCompras.model.UserGenerator;

import SeguiTusCompras.model.user.User;

public interface IUserFactory {

    public abstract User createUser(String name);

}
