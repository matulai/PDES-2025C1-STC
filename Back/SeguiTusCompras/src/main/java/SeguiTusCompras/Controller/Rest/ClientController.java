package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.User;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final UserService clientService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ClientController(UserService userService, ProductService productService, ProductMapper productMapper) {
        this.clientService = userService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping(value = "addProductToFavorites")
    public ResponseEntity<List<ProductDto>> addProductToFavorites(@RequestBody ProductDto productDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = clientService.getUser(username);
        Product product = productService.getProduct(productMapper.toEntityFromDto(productDto));
        User clientWithNewFavorite = clientService.addProductToFavorites(client, product);
        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.convertListToDto(clientWithNewFavorite.getFavorites()));
    }

    @PostMapping(value = "purchaseProducts")
    public ResponseEntity<List<PurchaseRecipeDto>> purchaseProducts(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = clientService.getUser(username);
        User clientWithPurchases = clientService.addPurchases(client);
        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.convertToListPurchaseRecipeDto(clientWithPurchases.getPurchases()));
    }

    @PostMapping(value = "addToCart")
    public ResponseEntity<List<ProductDto>> addToCart(@RequestBody ProductDto productDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = clientService.getUser(username);
        Product product = productMapper.toEntityFromDto(productDto);
        User clientUpdated = clientService.addToCart(client, product);
        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.convertListToDto(clientUpdated.getCart()));
    }

    @PostMapping(value = "removeFromCart")
    public ResponseEntity<List<ProductDto>> removeFromCart(@RequestBody ProductDto productDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = clientService.getUser(username);
        Product product = productMapper.toEntityFromDto(productDto);
        User clientUpdated = clientService.removeFromCart(client, product);
        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.convertListToDto(clientUpdated.getCart()));
    }

    @PostMapping(value = "qualifyProduct")
    public ResponseEntity<Void> qualifyProduct(@RequestBody QualificationDto qualificationDto){
        User client = clientService.getUser(qualificationDto.getUserName());
        Product product = productService.getProductByName(qualificationDto.getProductName()); 
        clientService.qualifyProduct(client, product, qualificationDto.getScore(), qualificationDto.getComment());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "userPurchases")
    public ResponseEntity<PaginationElementDto<PurchaseRecipeDto>> userPurchases(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PaginationElementDto<PurchaseRecipeDto> purchaseRecipeDtoPaginationElementDto = clientService.getPurchasesFromUser(username, page, size);
        return ResponseEntity.ok().body(purchaseRecipeDtoPaginationElementDto);
    }

    @GetMapping(value="userFavorites")
    public ResponseEntity<PaginationElementDto<ProductDto>> userFavorites(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "25") int size){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PaginationElementDto<ProductDto> productDtoPaginationElementDto = clientService.getFavorites(username, page, size);
        return ResponseEntity.ok().body(productDtoPaginationElementDto);
    }

}
