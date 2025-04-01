package SeguiTusCompras.Security;

import SeguiTusCompras.Controller.dtos.LoginDto;
import SeguiTusCompras.model.user.Admin;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    IUserDao userDao;

    public java.lang.String login(LoginDto request) {
        return null;
    }

    public java.lang.String register(java.lang.String name, java.lang.String password, java.lang.String role) {
        User user = userDao.getByName(name);
        if (user != null){
            throw new RuntimeException("user already registered");
        }
        this.generateNewUser(name, password, role);
        return name;
    }

    private User generateNewUser(java.lang.String name, java.lang.String password, java.lang.String role) {
        if (role.equals("Client")){
            Client client = new Client(name, password);
            userDao.save(client);
            return client;
        } else{
            Admin admin = new Admin(name, password);
            userDao.save(admin);
            return admin;
        }
    }
}
