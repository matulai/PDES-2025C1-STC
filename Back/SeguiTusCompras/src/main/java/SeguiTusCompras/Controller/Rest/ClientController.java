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
import SeguiTusCompras.Service.ClientService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.Client;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService ProductService;

    @PostMapping(value = "addProductToFavorites")
    public ResponseEntity<UserDto> addProductToFavorites(@RequestBody FavoriteDto favoriteDto){
        Client client = clientService.getClient(favoriteDto.getUserName());
        Product product = ProductService.getProductForClient(favoriteDto.getProductDto().getProductName());
        Client clientWithNewFavorite = clientService.addProductToFavorites(client, product);
        UserDto clientDto = UserMapper.convertToDto(clientWithNewFavorite);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PostMapping(value = "addPurchase")
    public ResponseEntity<Void> addPurchase(@RequestBody PurchaseDto purchaseDto){
        Client client = clientService.getClient(purchaseDto.getUserName());
        Product product = ProductService.getProductForClient(purchaseDto.getProductDto().getProductName());
        clientService.addPurchase(client, product, purchaseDto.getNumberOfUnits());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "qualifyProduct")
    public ResponseEntity<Void> qualifyProduct(@RequestBody QualificationDto qualificationDto){
        Client client = clientService.getClient(qualificationDto.getUserName());
        Product product = ProductService.getProductForClient(qualificationDto.getProductDto().getProductName()); 
        clientService.qualifyProduct(client, product, qualificationDto.getScore());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
