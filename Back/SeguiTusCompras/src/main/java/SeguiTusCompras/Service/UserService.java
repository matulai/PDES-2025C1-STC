package SeguiTusCompras.Service;

import SeguiTusCompras.Security.UserSecurity;
import SeguiTusCompras.model.UserGenerator.UserGenerator;
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
        User newUser = UserGenerator.getInstance().generateUser(role, name);
        User persistedUser = userDao.save(newUser);
        UserSecurity userSec = new UserSecurity(Role.valueOf(role), persistedUser, password);
        userSecurity.save(userSec);
        return persistedUser;
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
