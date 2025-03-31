package SeguiTusCompras.service;

import SeguiTusCompras.model.user.Admin;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserDao userDao;

    public User createClient(String name, String password, String role){
        User newUser = assingRoleToUser(name, password, role);
        userDao.save(newUser); // atajar error de usuario duplicado
        return newUser;
    }

    private static User assingRoleToUser(String name, String password, String role) {
        User newUser;
        if (role.equals(Role.Client.name())){
            newUser = new Client(name, password);
        } else{
            newUser = new Admin(name, password);
        }
        return newUser;
    }

    public User getUser(String name){
        return userDao.getByName(name);
    }

    public User updateUser(User updatedUser){
        // revisar que el usuario este en la base de datos
        return  userDao.save(updatedUser);
    }

    public void deleteUser(User user){
        userDao.delete(user);
    }

}
