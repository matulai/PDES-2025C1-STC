package SeguiTusCompras.model.user;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Purchase;
import SeguiTusCompras.model.Qualification;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Client extends User {
    @ManyToMany
    @JoinTable(
            name = "client_favorites",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favs = new HashSet<>(); ;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Purchase> purchases = new ArrayList<>(); 

    @OneToMany(mappedBy = "client")
    private Set<Qualification> qualifications = new HashSet<>(); ;

    public Client(java.lang.String name) {
        super(name);
    }

    public Client() {super();}

    public void purchase(Product product, Integer units){
        Purchase purchase = new Purchase(this, product, units);
        this.purchases.add(purchase);
    }

    public void addToFavs(Product product){
        this.favs.add(product);
    }

    public void qualifyPorduct(Product product, Integer score){
        if(doIOwnTheProduct(product)){
            Qualification qualification = new Qualification(this, product, score);
            product.makeQualification(qualification);
            qualifications.add(qualification);
        }
    }

    private boolean doIOwnTheProduct(Product product) {
        return this.purchases.stream().anyMatch(purchase -> purchase.getProduct().equals(product));
    }

}
