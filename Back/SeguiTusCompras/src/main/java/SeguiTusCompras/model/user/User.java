package SeguiTusCompras.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import SeguiTusCompras.model.PurchaseRecipe;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.report.UserReport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @ManyToMany
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseRecipe> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Qualification> qualifications = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @ManyToMany
    @JoinTable(
            name = "cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> cart = new ArrayList<>();

    public User(String name, String password, String role) {
        this.name = name;
        this.role = Role.valueOf(role);
        this.password = password;
    }
    
    @Override
    public String getUsername() {
        return getName();
    }

    public void addToPurchase(PurchaseRecipe purchaseRecipe){
        this.purchases.add(purchaseRecipe);
    }

    public void cleanCart() { this.cart.clear(); }

    public void addToCart(Product product) {
        this.cart.add(product);
    }

    public void removeFromCart(Product product) {
        this.cart.removeIf(p -> p.getMlaId().equals(product.getMlaId()));
    }

    public void addToFavorites(Product product){
        this.favorites.add(product);
    }

    public void deleteFromFavorites(Product product) {
        this.favorites.removeIf(p -> p.getMlaId().equals(product.getMlaId()));
    }

    public boolean ownsProduct(Product product) {
        return this.purchases.stream()
                .flatMap(recipe -> recipe.getPurchaseProducts().stream())
                .anyMatch(p -> p.getMlaId().equals(product.getMlaId()));
    }
}

