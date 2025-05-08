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
    @ManyToMany
    @JoinTable(
            name = "client_favorites",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favorites = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "client_purchases",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private Set<Qualification> qualifications = new HashSet<>();

    private void addToPurchases(Product product){
        this.purchases.add(product);
    }

    private void addToFavorites(Product product){
        this.favorites.add(product);
    }

    private void addToQualified(Product product, Integer score){
        if(doIOwnTheProduct(product)){
            Qualification qualification = new Qualification(this, product, score);
            qualifications.add(qualification);
        }
    }

    private boolean doIOwnTheProduct(Product product) {
        return this.purchases.contains(product);
    }

}
