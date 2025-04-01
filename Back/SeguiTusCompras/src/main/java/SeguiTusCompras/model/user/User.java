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
    private java.lang.String password;

    @OneToOne
    @JoinColumn(name = "userModel")
    private UserSecurity userSecurity;

    public User(java.lang.String name, java.lang.String password) {
        this.name = name;
        this.password = password;
    }
    public User() {}
}

