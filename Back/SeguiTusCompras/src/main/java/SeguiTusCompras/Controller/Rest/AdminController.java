package SeguiTusCompras.Controller.Rest;

import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Controller.Utils.ObjectMappers.QualificationMapper;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.dtos.UserPageDto;
import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.Controller.dtos.SimpleProductDto;
import SeguiTusCompras.Controller.dtos.SimpleUserDto;
import SeguiTusCompras.Service.ClientService;
import SeguiTusCompras.Service.ProductService;
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
    @Autowired
    private ProductService productService;

    @GetMapping(value = "registeredUsers")
    public ResponseEntity<UserPageDto> registeredUsers(@RequestParam(defaultValue = "0") int page){
        Page<User> clientsPage = clientService.getAllUserByRole(Role.Client, page);
        List<SimpleUserDto> clientsDto = UserMapper.convertPagedListToSimpleDto(clientsPage);
        return ResponseEntity.status(HttpStatus.OK).body(new UserPageDto(clientsDto, clientsPage.hasPrevious(), clientsPage.hasNext()));
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

    @GetMapping(value="topSellingProducts")
    public ResponseEntity<List<SimpleProductDto>> topSellingProducts(){
        List<Product> topSellingProducts = productService.getTopSellingProducts();
        List<SimpleProductDto> topSoldProductsDto = ProductMapper.convertToListSimpleDto(topSellingProducts);
        return ResponseEntity.ok().body(topSoldProductsDto);
    }

    @GetMapping(value="topBuyers")
    public ResponseEntity<List<SimpleUserDto>> topBuyers(){
        List<User> topBuyers = clientService.getTopBuyers();
        List<SimpleUserDto> topBuyersDto = UserMapper.convertListToSimpleDto(topBuyers);
        return ResponseEntity.status(HttpStatus.OK).body(topBuyersDto);
    }

    @GetMapping(value="topFavoriteProducts")
    public ResponseEntity<List<SimpleProductDto>> topFavoriteProducts(){
        List<Product> topSellingProducts = productService.getTopFavoriteProducts();
        List<SimpleProductDto> topSoldProductsDto = ProductMapper.convertToListSimpleDto(topSellingProducts);
        return ResponseEntity.ok().body(topSoldProductsDto);
    }
}
