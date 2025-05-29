package SeguiTusCompras.Controller.Rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.dtos.FavoriteDto;
import SeguiTusCompras.Controller.dtos.PurchaseDto;
import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.User;

import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping(value = "addPurchase")
    public ResponseEntity<Void> addPurchase(@RequestBody PurchaseDto purchaseDto){
        User client = clientService.getUser(purchaseDto.getUserName());
        Product product = productService.getProduct(productMapper.toEntityFromDto(purchaseDto.getProductDto()));
        clientService.addPurchase(client, product);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "qualifyProduct")
    public ResponseEntity<Void> qualifyProduct(@RequestBody QualificationDto qualificationDto){
        User client = clientService.getUser(qualificationDto.getUserName());
        Product product = productService.getProductByName(qualificationDto.getProductName()); 
        clientService.qualifyProduct(client, product, qualificationDto.getScore(), qualificationDto.getComment());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
