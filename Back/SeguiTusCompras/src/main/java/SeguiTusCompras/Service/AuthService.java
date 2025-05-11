package SeguiTusCompras.Service;

import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private IUserDao userDao;

    public User createUser(String name, String password, String role){
        User user = userDao.getByName(name);
        if (user != null){
            throw new RuntimeException(ServicesErrors.ALREADY_REGISTERED.getMessage());
        }
        return this.generateNewUser(name, password, role);
    }

    private User generateNewUser(String name, String password,  String role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        User newUser = new User(name, encodedPassword, role);
        return userDao.save(newUser);
    }

    public User getUser(String name, String password){
        User user = Optional.ofNullable(this.userDao.getByName(name))
                .orElseThrow(() -> new RuntimeException(ServicesErrors.INVALID_PASSWORD_OR_USERNAME.getMessage()));
        validatePassWord(user.getPassword(), password);
        return user;
    }


    private void validatePassWord(String encodedPassword, String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(rawPassword, encodedPassword)){
            throw  new RuntimeException(ServicesErrors.INVALID_PASSWORD_OR_USERNAME.getMessage()); // cambiar
        }
    }

    public void deleteUsers() {
        userDao.deleteAll();
    }

}
