package SeguiTusCompras.modelo.user;

import jakarta.persistence.*;

@Entity
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    public Admin(String name, String password) {
        super(name, password);
    }
}
