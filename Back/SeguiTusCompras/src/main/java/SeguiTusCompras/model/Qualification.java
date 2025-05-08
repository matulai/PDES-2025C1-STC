package SeguiTusCompras.model;

import SeguiTusCompras.model.user.Client;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Qualification {

    public Qualification(Client client, Product product, Integer score, String comment) {
        this.client = client;
        this.product = product;
        this.score = score;
        this.comment = comment;
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

    @Column(nullable = false)
    private Integer score;

    private String comment;

}
