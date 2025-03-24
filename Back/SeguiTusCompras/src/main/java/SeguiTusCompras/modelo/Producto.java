package SeguiTusCompras.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String MLA_id;

    @Column(nullable = false)
    private String categoria_id;

    @Column(nullable = false)
    private String nombre;

    @Column(precision = 12, scale = 3)
    private BigDecimal precio;

    @Column(nullable = false)
    private String imagen_url;

    @Column
    private int cantidad;

    @Column
    private String description;

}
