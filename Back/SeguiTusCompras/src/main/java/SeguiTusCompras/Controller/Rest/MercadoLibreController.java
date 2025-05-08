package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Service.Integration.MercadoLibreService;
import SeguiTusCompras.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class MercadoLibreController {

    private final MercadoLibreService mercadoLibreService;

    public MercadoLibreController(MercadoLibreService mercadoLibreService) {
        this.mercadoLibreService = mercadoLibreService;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id){
        return mercadoLibreService.getProductById(id);
    }

    @GetMapping("/search/{text}")
    public List<Product> getProductsByKeyword(@PathVariable String text) {
        return mercadoLibreService.searchProducts(text);
    }
}
