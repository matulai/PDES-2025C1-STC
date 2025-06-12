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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private UserReport report;


    public User(String name, String password, String role) {
        this.name = name;
        this.role = Role.valueOf(role);
        this.password = password;
    }
    
    
    @Override
    public String getUsername() {
        return getName();
    }

    public void addToPurchases(Product product){
        this.purchases.add(product);
        this.report.addPurchase();
        product.increasePurchasesCounter();
    }


    public void addToFavorites(Product product){
        this.favorites.add(product);
        product.increaseFavoritesCounter();
    }

    public void deleteFromFavorites(Product product) {
        this.favorites.remove(product);
        product.decreaseFavoritesCounter();
    }

    public void qualifyProduct(Product product, Integer score){
        if(doIOwnTheProduct(product)){
            Qualification qualification = new Qualification(this, score, product);
            qualifications.add(qualification);
        }
    }


    private boolean doIOwnTheProduct(Product product) {
        return this.purchases.contains(product);
    }
    
  

    public Comment generateComment(String comment, Qualification qualification) {
        Comment newComment = new Comment(comment, qualification);
        qualification.setComment(newComment);
        return newComment;
    }


    public Qualification getQualificationForProduct(Product product) {
        for(Qualification qualification : qualifications){
            if(qualification.getProduct().getName().equals(product.getName())){
                return qualification;
            }
        }
        return null;
    }
}

