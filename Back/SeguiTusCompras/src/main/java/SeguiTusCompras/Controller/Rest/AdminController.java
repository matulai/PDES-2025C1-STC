package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.*;
import SeguiTusCompras.model.PurchaseRecipe;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SeguiTusCompras.Controller.Utils.ObjectMappers.QualificationMapper;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
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
    public ResponseEntity<UserPageDto> allRegisteredUsers(@RequestParam(defaultValue = "0") int page){
        Page<User> clientsPage = clientService.getAllUserByRole(Role.Client, page);
        List<SimpleUserDto> clientsDto = UserMapper.convertPagedListToSimpleDto(clientsPage);
        return ResponseEntity.status(HttpStatus.OK).body(new UserPageDto(clientsDto, clientsPage.hasPrevious(), clientsPage.hasNext()));
    }

    @GetMapping(value = "allUsersQualifications")
    public ResponseEntity<List<QualificationDto>> allUsersQualifications(){
        List<Qualification> qualifications = clientService.getAllUsersQualifications();
        List<QualificationDto> qualificationsDto = QualificationMapper.convertListToDto(qualifications);
        return ResponseEntity.ok().body(qualificationsDto);
    }

    @GetMapping(value = "allFavoritesProducts")
    public ResponseEntity<List<ProductDto>> allFavoritesProducts(){
        List<Product> products = productService.getAllFavoritesProducts();
        List<ProductDto> productsDtos = ProductMapper.convertListToDto(products);
        return ResponseEntity.ok().body(productsDtos);
    }

    @GetMapping(value = "allUsersPurchases")
    public ResponseEntity<List<PurchaseRecipeDto>> allUsersPurchases(){
        List<PurchaseRecipe> purchaseRecipes = clientService.getAllUsersPurchases();
        List<PurchaseRecipeDto> purchaseRecipesDtos = ProductMapper.convertToListPurchaseRecipeDto(purchaseRecipes);
        return ResponseEntity.ok().body(purchaseRecipesDtos);
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
