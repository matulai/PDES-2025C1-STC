package SeguiTusCompras.Controller.Rest;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Controller.Utils.ObjectMappers.QualificationMapper;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.dtos.ProductDto;
import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.Controller.dtos.SimpleProductDto;
import SeguiTusCompras.Controller.dtos.SimpleUserDto;
import SeguiTusCompras.Service.ClientService;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private ClientService clientService;

    @GetMapping(value = "registeredUsers")
    public ResponseEntity<List<SimpleUserDto>> registeredUsers(){
        List<User> clients = clientService.getAllUserByRole(Role.Client);
        List<SimpleUserDto> clientsDto = UserMapper.convertListToSimpleDto(clients);
        return ResponseEntity.status(HttpStatus.OK).body(clientsDto);
    }

    @GetMapping(value = "userQualifications")
    public ResponseEntity<List<QualificationDto>> userQualifications(@RequestParam String userName){
        List<Qualification> qualifications = clientService.getQualifications(userName);
        List<QualificationDto> qualificationsDto = QualificationMapper.convertListToDto(qualifications);
        return ResponseEntity.ok().body(qualificationsDto);
    }

    @GetMapping(value = "userPurchases")
    public ResponseEntity<List<SimpleProductDto>> userPurchases(@RequestParam String userName){
        List<Product> purchases = clientService.getPurchases(userName);
        List<SimpleProductDto> productsDtos = ProductMapper.convertToListSimpleDto(purchases);
        return ResponseEntity.ok().body(productsDtos);
    }

    @GetMapping(value="userFavorites")
    public ResponseEntity<List<SimpleProductDto>> userFavorites(@RequestParam String userName){
        List<Product> favorites = clientService.getFavorites(userName);
        List<SimpleProductDto> favoritesDto = ProductMapper.convertToListSimpleDto(favorites);
        return ResponseEntity.ok().body(favoritesDto);
    }
}
