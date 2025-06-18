package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.model.user.Role;

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
    public ResponseEntity<PaginationElementDto<ProductDto>> topSellingProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PaginationElementDto<ProductDto> productPaginationElementDto = productService.getTopSellingProducts(page, size);
        return ResponseEntity.ok().body(productPaginationElementDto);
    }

    @GetMapping(value="topBuyers")
    public ResponseEntity<PaginationElementDto<SimpleUserDto>> topBuyers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PaginationElementDto<SimpleUserDto> simpleUserDtoPaginationElementDto = clientService.getTopBuyers(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(simpleUserDtoPaginationElementDto);
    }

    @GetMapping(value="topFavoriteProducts")
    public ResponseEntity<PaginationElementDto<ProductDto>> topFavoriteProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PaginationElementDto<ProductDto> productDtoPaginationElementDto = productService.getTopFavoriteProducts(page, size);
        return ResponseEntity.ok().body(productDtoPaginationElementDto);
    }
}
