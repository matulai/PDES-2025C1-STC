package SeguiTusCompras.model.user;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Client extends User {
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
    private Set<Product> favs;

    @ManyToMany
    @JoinTable(
            name = "client_purchases",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> purchases;

    @OneToMany(mappedBy = "client")
    private Set<Qualification> qualifications;

    public Client(java.lang.String name) {
        super(name);
    }

    public Client() {super();}

    private void purchase(Product product){
        this.purchases.add(product);
    }

    private void addToFavs(Product product){
        this.favs.add(product);
    }

    private void qualifyPorduct(Product product, Integer score){
        if(doIOwnTheProduct(product)){
            Qualification qualification = new Qualification(this, product, score);
            product.makeQualification(qualification);
            qualifications.add(qualification);
        }
    }

    private boolean doIOwnTheProduct(Product product) {
        return this.purchases.contains(product);
    }

}
