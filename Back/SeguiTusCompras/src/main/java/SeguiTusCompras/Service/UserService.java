package SeguiTusCompras.service;

import SeguiTusCompras.Security.UserSecurity;
import SeguiTusCompras.model.user.Admin;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.persistence.IUserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserDao userDao;
    @Autowired
    IUserSecurity userSecurity;

    public User createUser(String name, String password, String role){
        User user = userDao.getByName(name);
        if (user != null){
            throw new RuntimeException("user already registered");
        }
        return this.generateNewUser(name, password, role);
    }

    private User generateNewUser(String name, String password,  String role) {
        if (role.equals("Client")){
            Client client = new Client(name, password);
            userDao.save(client);
            userSecurity.save(new UserSecurity( Role.valueOf(role), client));
            return client;
        } else {
            Admin admin = new Admin(name, password);
            userDao.save(admin);
            userSecurity.save(new UserSecurity( Role.valueOf(role), admin));
            return admin;
        }
    }

    public User getUser(java.lang.String name){
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
