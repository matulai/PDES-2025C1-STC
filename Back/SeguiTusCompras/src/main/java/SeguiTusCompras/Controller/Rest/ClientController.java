package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.*;
import SeguiTusCompras.model.PurchaseRecipe;
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
    public ResponseEntity<List<ProductDto>> addProductToFavorites(@RequestBody FavoriteDto favoriteDto){
        User client = clientService.getUser(favoriteDto.getUserName());
        Product product = productService.getProduct(productMapper.toEntityFromDto(favoriteDto.getProductDto()));
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
    public ResponseEntity<List<ProductDto>> addToCart(@RequestBody CartDto cartDto){
        User client = clientService.getUser(cartDto.getUserName());
        Product product = productMapper.toEntityFromDto(cartDto.getProductDto());
        User clientUpdated = clientService.addToCart(client, product);
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
    public ResponseEntity<List<PurchaseRecipeDto>> userPurchases(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PurchaseRecipe> purchasesRecipes = clientService.getPurchasesFromUser(username);
        List<PurchaseRecipeDto> purchasesRecipesDto = ProductMapper.convertToListPurchaseRecipeDto(purchasesRecipes);
        return ResponseEntity.ok().body(purchasesRecipesDto);
    }

    @GetMapping(value="userFavorites")
    public ResponseEntity<List<ProductDto>> userFavorites(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Product> favorites = clientService.getFavorites(username);
        List<ProductDto> favoritesDto = ProductMapper.convertListToDto(favorites);
        return ResponseEntity.ok().body(favoritesDto);
    }

}
