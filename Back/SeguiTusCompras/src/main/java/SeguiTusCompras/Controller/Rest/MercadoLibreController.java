package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.ProductDto;
import SeguiTusCompras.Service.Integration.MercadoLibreService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.model.Product;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Service.utils.ProductMapper;

import java.util.List;

@RestController
@RequestMapping("/products")
public class MercadoLibreController {

    private final MercadoLibreService mercadoLibreService;
    private final ProductService productService;

    public MercadoLibreController(MercadoLibreService mercadoLibreService, ProductService productService) {
        this.mercadoLibreService = mercadoLibreService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable String id){
        Product product = productService.getProductByMlaId(id);
        if (product == null) {
            product = mercadoLibreService.getProductById(id);
        }
        return ProductMapper.converToDto(product);
    }

    @GetMapping("/search")
    public List<ProductDto> getProductsByKeyword(
            @RequestParam String text,
            @RequestParam(required = false) String category) {
        List<Product> products = mercadoLibreService.searchProducts(text, category);
        return ProductMapper.convertListToDto(products);
    }
}
