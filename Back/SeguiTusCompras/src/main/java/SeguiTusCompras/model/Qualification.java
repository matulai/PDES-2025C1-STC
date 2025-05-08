package SeguiTusCompras.model;

import SeguiTusCompras.model.user.Client;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
public class Qualification {

    public Qualification(Client client, Product product, Integer score) {
        this.client = client;
        this.product = product;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "client_id", nullable = false)
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String comment;

    @Column(nullable = false)
    private Integer score;
}
