package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.User;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private UserService clientService;
    @Autowired
    private ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping(value = "addProductToFavorites")
    public ResponseEntity<UserDto> addProductToFavorites(@RequestBody FavoriteDto favoriteDto){
        User client = clientService.getUser(favoriteDto.getUserName());
        Product product = productService.getProduct(productMapper.toEntityFromDto(favoriteDto.getProductDto()));
        User clientWithNewFavorite = clientService.addProductToFavorites(client, product);
        UserDto clientDto = UserMapper.convertToDto(clientWithNewFavorite);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PostMapping(value = "purchaseProducts")
    public ResponseEntity<Void> purchaseProducts(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = clientService.getUser(username);
        clientService.addPurchases(client);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "addToCart")
    public ResponseEntity<Void> addToCart(@RequestBody CartDto cartDto){
        User client = clientService.getUser(cartDto.getUserName());
        Product product = productMapper.toEntityFromDto(cartDto.getProductDto());
        clientService.addToCart(client, product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "qualifyProduct")
    public ResponseEntity<Void> qualifyProduct(@RequestBody QualificationDto qualificationDto){
        User client = clientService.getUser(qualificationDto.getUserName());
        Product product = productService.getProductByName(qualificationDto.getProductName()); 
        clientService.qualifyProduct(client, product, qualificationDto.getScore(), qualificationDto.getComment());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "userPurchases")
    public ResponseEntity<List<ProductDto>> userPurchases(@RequestParam String userName){
        List<Product> purchases = clientService.getPurchasesFromUser(userName);
        List<ProductDto> productsDtos = ProductMapper.convertListToDto(purchases);
        return ResponseEntity.ok().body(productsDtos);
    }

    @GetMapping(value="userFavorites")
    public ResponseEntity<List<ProductDto>> userFavorites(@RequestParam String userName){
        List<Product> favorites = clientService.getFavorites(userName);
        List<ProductDto> favoritesDto = ProductMapper.convertListToDto(favorites);
        return ResponseEntity.ok().body(favoritesDto);
    }

}
