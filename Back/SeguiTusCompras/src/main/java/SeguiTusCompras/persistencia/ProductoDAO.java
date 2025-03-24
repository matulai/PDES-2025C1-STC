package SeguiTusCompras.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import SeguiTusCompras.modelo.Producto;

interface ProductoDAO extends JpaRepository<Producto, Long> {

}
