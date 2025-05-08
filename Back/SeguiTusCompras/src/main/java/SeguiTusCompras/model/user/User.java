package SeguiTusCompras.model.user;

import SeguiTusCompras.Security.UserSecurity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "user_model")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_security_id")
    private UserSecurity userSecurity;

    public User(String name) {
        this.name = name;
    }
    public User() {}
}

