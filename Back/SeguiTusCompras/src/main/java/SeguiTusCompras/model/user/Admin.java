package SeguiTusCompras.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    public Admin(String name, String password) {
        super(name, password);
    }
}
