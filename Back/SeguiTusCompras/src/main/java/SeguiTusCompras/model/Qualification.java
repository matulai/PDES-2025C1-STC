package SeguiTusCompras.model;

import SeguiTusCompras.model.Product.Product;
import SeguiTusCompras.model.user.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Qualification {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name= "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer score;

    public Qualification(Client client, Product product, Integer score) {}
}
