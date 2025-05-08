package SeguiTusCompras.model.user;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Client extends User {

    public Client(String name) {
        super(name);
    }

    public Client() {super();}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "client_favorites",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favorites = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "client_purchases",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Qualification> qualifications = new HashSet<>();

    public void addToPurchases(Product product){
        this.purchases.add(product);
    }

    public void addToFavorites(Product product){
        this.favorites.add(product);
    }

    public void addToQualified(Product product, Integer score, String comment){
        if(doIOwnTheProduct(product)){
            Qualification qualification = new Qualification(this, product, score, comment);
            product.makeQualification(qualification);
            qualifications.add(qualification);
        }
    }

    private boolean doIOwnTheProduct(Product product) {
        return this.purchases.contains(product);
    }

}
