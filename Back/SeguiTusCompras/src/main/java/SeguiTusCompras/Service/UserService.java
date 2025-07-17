package SeguiTusCompras.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import SeguiTusCompras.Controller.Utils.ObjectMappers.QualificationMapper;
import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.dtos.*;
import SeguiTusCompras.Service.utils.Pagination;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.persistence.IProductDao;
import SeguiTusCompras.persistence.IPurchaseRecipeDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import SeguiTusCompras.Error.ErrorMessages;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IQualificationDAO;
import SeguiTusCompras.persistence.IUserDao;

@Service
public class UserService {

    private final IUserDao userDao;
    private final IQualificationDAO qualificationDao;
    private final IPurchaseRecipeDao purchaseRecipeDao;
    private final IProductDao productDao;

    public UserService(IUserDao userDao, IQualificationDAO qualificationDao,
                       IPurchaseRecipeDao purchaseRecipeDao, IProductDao productDao) {
        this.userDao = userDao;
        this.qualificationDao = qualificationDao;
        this.purchaseRecipeDao = purchaseRecipeDao;
        this.productDao = productDao;
    }

    public  User getUser(String userName) {
        return Optional.ofNullable(userDao.getByName(userName))
        .orElseThrow(() -> new NoSuchElementException(ErrorMessages.USER_NOT_FOUND.getMessage()));
    }

    private Product findOrCreate(Product product) {
        return productDao.findByMlaId(product.getMlaId())
                .orElseGet(() -> productDao.save(product));
    }

    public User addProductToFavorites(User client, Product product) {
        Product attachedProduct = findOrCreate(product);

        if (client.getFavorites().contains(attachedProduct)) {
            client.deleteFromFavorites(attachedProduct);
        } else {
            client.addToFavorites(attachedProduct);
        }

        return userDao.save(client);
    }

    public User addToCart(User user, Product product) {
        Product attachedProduct = findOrCreate(product);
        user.addToCart(attachedProduct);
        return userDao.save(user);
    }

    public User removeFromCart(User user, Product product) {
        user.removeFromCart(product);
        return userDao.save(user);
    }

    public User addPurchases(User user) {
        PurchaseRecipe purchaseRecipe = purchaseRecipeDao.save(new PurchaseRecipe(user.getCart(), user));
        user.addToPurchase(purchaseRecipe);
        user.cleanCart();
        return userDao.save(user);
    }

    public void qualifyProduct(User user, Product product, Integer score, String comment) {
        checkIfScoreIsValid(score);
        if(user.ownsProduct(product)) {
            Product productFromDB = findOrCreate(product);
            qualificationDao.save(new Qualification(user, score, productFromDB, comment));
        }
    }

    private void checkIfScoreIsValid(Integer score) {
        if(score < 1 || score > 5) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_SCORE.getMessage());
        }
    }

    public PaginationElementDto<PurchaseRecipeDto> getPurchasesFromUser(String userName, int page, int size) {
        Page<PurchaseRecipe> purchaseRecipePage = userDao.getPurchasesPage(userName, PageRequest.of(page - 1, size));
        List<PurchaseRecipeDto> purchaseRecipeDtoList = purchaseRecipePage.getContent()
                .stream()
                .map(ProductMapper::converToPurchaseRecipeDto)
                .toList();
        Pagination pagination = new Pagination(page, size, purchaseRecipePage.getTotalElements());

        return new PaginationElementDto<>(purchaseRecipeDtoList, pagination);
    }

    public PaginationElementDto<ProductDto> getFavorites(String userName, int page, int size) {
        Page<Product> productPage = userDao.getFavoritesPage(userName, PageRequest.of(page - 1, size));
        List<ProductDto> productDtoList = productPage.getContent()
                .stream()
                .map(ProductMapper::converToDto)
                .toList();
        Pagination pagination = new Pagination(page, size, productPage.getTotalElements());
        return new PaginationElementDto<>(productDtoList, pagination);
    }

    public PaginationElementDto<SimpleUserDto> getAllUserByRole(Role role, int page, int size) {
        Page<User> userPage = userDao.UsersByRole(role, PageRequest.of(page - 1, size));
        List<SimpleUserDto> simpleUserDtoList = userPage.getContent()
                .stream()
                .map(UserMapper::convertToSimpleDto)
                .toList();
        Pagination pagination = new Pagination(page, size, userPage.getTotalElements());

        return new PaginationElementDto<>(simpleUserDtoList, pagination);
    }

    public PaginationElementDto<QualificationDto> getAllUsersQualifications(int page, int size) {
        Page<Qualification> qualificationPage = qualificationDao.findAllPage(PageRequest.of(page - 1, size));
        List<QualificationDto> qualificationDtoList = qualificationPage.getContent()
                .stream()
                .map(QualificationMapper::convertToDto)
                .toList();
        Pagination pagination = new Pagination(page, size, qualificationPage.getTotalElements());
        return new PaginationElementDto<>(qualificationDtoList, pagination);
    }

    public PaginationElementDto<PurchaseRecipeDto> getAllUsersPurchases(int page, int size) {
        Page<PurchaseRecipe> purchaseRecipePage = purchaseRecipeDao.findAllPage(PageRequest.of(page - 1, size));
        List<PurchaseRecipeDto> purchaseRecipeDtoList = purchaseRecipePage.getContent()
                .stream()
                .map(ProductMapper::converToPurchaseRecipeDto)
                .toList();
        Pagination pagination = new Pagination(page, size, purchaseRecipePage.getTotalElements());
        return new PaginationElementDto<>(purchaseRecipeDtoList, pagination);
    }

    public PaginationElementDto<SimpleUserDto> getTopBuyers(int page, int size) {
        Page<User> userPage = userDao.findTopClientsByPurchases(Role.Client, PageRequest.of(page - 1, size));
        List<SimpleUserDto> simpleUserDtoList = userPage.getContent()
                .stream()
                .map(UserMapper::convertToSimpleDto)
                .toList();
        Pagination pagination = new Pagination(page, size, userPage.getTotalElements());

        return new PaginationElementDto<>(simpleUserDtoList, pagination);
    }

}
