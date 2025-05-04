package SeguiTusCompras.model;

import SeguiTusCompras.model.user.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
