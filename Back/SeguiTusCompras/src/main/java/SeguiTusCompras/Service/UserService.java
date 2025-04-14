package SeguiTusCompras.Service;

import SeguiTusCompras.Errors.ServicesErrors;
import SeguiTusCompras.Security.UserSecurity;
import SeguiTusCompras.model.UserGenerator.UserGenerator;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.persistence.IUserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    IUserDao userDao;
    @Autowired
    IUserSecurity userSecurity;

    public User createUser(String name, String password, String role){
        User user = userDao.getByName(name);
        if (user != null){
            throw new RuntimeException(ServicesErrors.ALREADY_REGISTERED.getMessage());
        }
        return this.generateNewUser(name, password, role);
    }

    private User generateNewUser(String name, String password,  String role) {
        User newUser = UserGenerator.getInstance().generateUser(role, name);
        User persistedUser = userDao.save(newUser);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        UserSecurity securityUser = new UserSecurity(Role.valueOf(role), persistedUser, encodedPassword);
        userSecurity.save(securityUser);
        newUser.setUserSecurity(securityUser);
        return userDao.save(newUser);
    }

    public User getUser(String name, String password){
        UserSecurity userSecurity = Optional.ofNullable(this.userSecurity.getByName(name))
                .orElseThrow(() -> new RuntimeException(ServicesErrors.INVALID_PASSWORD_OR_USERNAME.getMessage()));
        validatePassWord(userSecurity.getPassword(), password);
        return userDao.getByName(name);
    }


    private void validatePassWord(String encodedPassword, String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(rawPassword, encodedPassword)){
            throw  new RuntimeException(ServicesErrors.INVALID_PASSWORD_OR_USERNAME.getMessage()); // cambiar
        }
    }

    public User updateUser(User updatedUser){
        // revisar que el usuario este en la base de datos
        return  userDao.save(updatedUser);
    }

    public void deleteUser(User user){
        userDao.delete(user);
    }

}
