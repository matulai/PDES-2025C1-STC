package SeguiTusCompras.Security;

import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class UserSecurity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Role role;

    @OneToOne
    @JoinColumn(name = "userSecurity")
    private User userModel;

    public UserSecurity(Role role, User client) {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public java.lang.String getPassword() {
        return "";
    }

    @Override
    public java.lang.String getUsername() {
        return "";
    }
}
