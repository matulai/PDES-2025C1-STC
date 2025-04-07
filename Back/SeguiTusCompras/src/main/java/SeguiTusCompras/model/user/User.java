package SeguiTusCompras.model.user;

import SeguiTusCompras.Security.UserSecurity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "userModel")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private java.lang.String name;

    @OneToOne
    @JoinColumn(name = "userSecurity")
    private UserSecurity userSecurity;

    public User(String name) {
        this.name = name;
    }
    public User() {}
}

