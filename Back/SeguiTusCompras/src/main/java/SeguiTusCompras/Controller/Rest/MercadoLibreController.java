package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Service.Integration.MercadoLibreService;
import SeguiTusCompras.model.Product;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public List<Product> getProductsByKeyword(
            @RequestParam String text,
            @RequestParam(required = false) String category) {
        return mercadoLibreService.searchProducts(text, category);
    }
}
