package SeguiTusCompras.modelo.user;

import jakarta.persistence.Entity;
import  ninguno.SeguiTusCompras.usuario.Producto;
import java.util.Set;

@Entity
public class Client extends User {
    private Set<Producto> favs;
    private Set<Producto> purchases;

    public Client(String name, String pass) {
        super(name, pass);
    }
}
