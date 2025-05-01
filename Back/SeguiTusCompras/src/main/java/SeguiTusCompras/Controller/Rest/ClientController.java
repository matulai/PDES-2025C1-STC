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

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping(value = "addProductToFavorites")
    public ResponseEntity<UserDto> addProductToFavorites(FavoriteDto favoriteDto){
        Client client = clientService.getClient(favoriteDto.getUserName());
        Product product = ProductService.getProductForClient(favoriteDto.getProductDto().getProductName());
        Client clientWithNewFavorite = clientService.addProductToFavorites(client, product);
        UserDto clientDto = UserMapper.convertToDto(clientWithNewFavorite);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PostMapping(value = "addPurchase")
    public ResponseEntity<UserDto> addPurchase(PurchaseDto purchaseDto){
        Client client = clientService.getClient(purchaseDto.getUserName());
        Product product = ProductService.getProductForClient(purchaseDto.getProductDto().getProductName());
        Client clientWithNewPurchase = clientService.addPurchase(client, product);
        UserDto clientDto = UserMapper.convertToDto(clientWithNewPurchase);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PostMapping(value = "qualifyProduct")
    public ResponseEntity<UserDto> qualifyProduct(QualificationDto qualificationDto){
        Client client = clientService.getClient(qualificationDto.getUserName());
        Product product = ProductService.getProductForClient(qualificationDto.getProductDto().getProductName()); 
        Client clientWithNewQualification = clientService.qualifyProduct(client, product, qualificationDto.getScore());
        UserDto clientDto = UserMapper.convertToDto(clientWithNewQualification);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

}
