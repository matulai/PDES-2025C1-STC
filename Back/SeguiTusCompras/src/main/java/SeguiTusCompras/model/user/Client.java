package SeguiTusCompras.model.user;

import SeguiTusCompras.model.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Dictionary;
import java.util.Set;

@Entity
@Data
@Getter
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Product> favs;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Product> purchases;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Dictionary<Product, Integer> qualifications; // ver como se comporta al persistir

    public Client(String name, String pass) {
        super(name, pass);
    }

    private void purchase(Product product){
        this.purchases.add(product);
    }

    private void addToFavs(Product product){
        this.favs.add(product);
    }

    private void qualifyPorduct(Product product, Integer score){
        if(this.purchases.contains(product)){
            product.receiveQualification(score, this);
        }
    }

    public Long getId() {
        return this.id;
    }

    public void qualificationAdded(Product product, Integer score) {
        qualifications.put(product, score);
    }
}
