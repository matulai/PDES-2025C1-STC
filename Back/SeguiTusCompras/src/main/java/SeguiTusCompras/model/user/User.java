package SeguiTusCompras.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SeguiTusCompras.model.Comment;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_table")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Role role;

    private String password; 

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favorites = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "purchase",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Qualification> qualifications = new HashSet<>();


     public User(String name, String password, String role) {
        this.name = name;
        this.role = Role.valueOf(role);
        this.password = password;
    }

    public void addToPurchases(Product product){
        this.purchases.add(product);
    }

    public void addToFavorites(Product product){
        this.favorites.add(product);
    }

    public void addToQualified(Product product, Integer score){
        if(doIOwnTheProduct(product)){
            Qualification qualification = new Qualification(this, score, product);
            qualifications.add(qualification);
        }
    }


    private boolean doIOwnTheProduct(Product product) {
        return this.purchases.contains(product);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }
    @Override
    public String getUsername() {
        return getName();
    }


    public Comment generateComment(String comment, Qualification qualification) {
        return new Comment(comment, qualification);
    }
}

