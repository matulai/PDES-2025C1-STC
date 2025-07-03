package SeguiTusCompras.Service;

import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.Error.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class AuthService {
    @Autowired
    private IUserDao userDao;

    public User createUser(String name, String password, String role){
        checkUserHasValidRole(role);
        User persistedUser = userDao.getByName(name);
        if (persistedUser != null){
            throw new RuntimeException(ErrorMessages.ALREADY_REGISTERED.getMessage());
        }
        return this.generateNewUser(name, password, role);
    }

    private User generateNewUser(String name, String password, String role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
        String encodedPassword = encoder.encode(password);
        User newUser = new User(name, encodedPassword, role);
        User persistedUser = userDao.save(newUser);
        return userDao.save(persistedUser);
    }

    private void checkUserHasValidRole(String role) {
    if (role == null || (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Client"))) {
        throw new IllegalArgumentException(ErrorMessages.INVALID_ROLE.getMessage());
    }
}


    public User getUser(String name, String password){
        User user = Optional.ofNullable(this.userDao.getByName(name))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.INVALID_PASSWORD_OR_USERNAME.getMessage()));
        validatePassWord(user.getPassword(), password);
        return user;
    }

    public User getUserByName(String name) {
        User user = this.userDao.getByName(name);
        if (user == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
        return user;
    }

    private void validatePassWord(String encodedPassword, String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(rawPassword, encodedPassword)){
            throw  new IllegalArgumentException(ErrorMessages.INVALID_PASSWORD_OR_USERNAME.getMessage()); 
        }
    }

    public void deleteUsers() {
        userDao.deleteAll();
    }

}
