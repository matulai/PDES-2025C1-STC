package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService clientService;
    private final ProductService productService;

    public AdminController(UserService clientService, ProductService productService) {
        this.productService = productService;
        this.clientService = clientService;
    }

    @GetMapping(value = "allRegisteredUsers")
    public ResponseEntity<PaginationElementDto<SimpleUserDto>> allRegisteredUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "25") int size){
        PaginationElementDto<SimpleUserDto> simpleUserDtoPaginationElementDto = clientService.getAllUserByRole(Role.Client, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(simpleUserDtoPaginationElementDto);
    }

    @GetMapping(value = "allUsersQualifications")
    public ResponseEntity<PaginationElementDto<QualificationDto>> allUsersQualifications(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "25") int size){
        PaginationElementDto<QualificationDto> qualificationDtoPaginationElementDto = clientService.getAllUsersQualifications(page, size);
        return ResponseEntity.ok().body(qualificationDtoPaginationElementDto);
    }

    @GetMapping(value = "allFavoritesProducts")
    public ResponseEntity<PaginationElementDto<ProductDto>> allFavoritesProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "25") int size){
        PaginationElementDto<ProductDto> productDtoPaginationElementDto = productService.getAllFavoritesProducts(page, size);
        return ResponseEntity.ok().body(productDtoPaginationElementDto);
    }

    @GetMapping(value = "allUsersPurchases")
    public ResponseEntity<PaginationElementDto<PurchaseRecipeDto>> allUsersPurchases(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PaginationElementDto<PurchaseRecipeDto> purchaseRecipeDtoPaginationElementDto = clientService.getAllUsersPurchases(page, size);
        return ResponseEntity.ok().body(purchaseRecipeDtoPaginationElementDto);
    }

    @GetMapping(value="topSellingProducts")
    public ResponseEntity<List<ProductDto>> topSellingProducts(){
        List<Product> topSellingProducts = productService.getTopSellingProducts();
        List<ProductDto> topSoldProductsDto = ProductMapper.convertListToDto(topSellingProducts);
        return ResponseEntity.ok().body(topSoldProductsDto);
    }

    @GetMapping(value="topBuyers")
    public ResponseEntity<List<SimpleUserDto>> topBuyers(){
        List<User> topBuyers = clientService.getTopBuyers();
        List<SimpleUserDto> topBuyersDto = UserMapper.convertListToSimpleDto(topBuyers);
        return ResponseEntity.status(HttpStatus.OK).body(topBuyersDto);
    }

    @GetMapping(value="topFavoriteProducts")
    public ResponseEntity<List<ProductDto>> topFavoriteProducts(){
        List<Product> topSellingProducts = productService.getTopFavoriteProducts();
        List<ProductDto> topSoldProductsDto = ProductMapper.convertListToDto(topSellingProducts);
        return ResponseEntity.ok().body(topSoldProductsDto);
    }
}
