package SeguiTusCompras.model.user;

import SeguiTusCompras.model.Product;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Product> favs;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Product> purchases;

    public Client(String name, String pass) {
        super(name, pass);
    }
}
