package SeguiTusCompras.Service;

import java.util.List;
import java.util.Optional;

import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.persistence.IProductDao;
import SeguiTusCompras.persistence.IPurchaseRecipeDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        .orElseThrow(() -> new RuntimeException(ErrorMessages.USER_NOT_FOUND.getMessage()));
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
            throw new RuntimeException(ErrorMessages.INVALID_SCORE.getMessage());
        }
    }

    public List<PurchaseRecipe> getPurchasesFromUser(String userName) {
        return userDao.getPurchases(userName);
    }

    public List<Product> getFavorites(String userName) {
        return userDao.getFavorites(userName);
    }

    public Page<User> getAllUserByRole(Role role, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userDao.UsersByRole(role, pageable);
    }

    public List<Qualification> getAllUsersQualifications() {
        return qualificationDao.findAll();
    }

    public List<PurchaseRecipe> getAllUsersPurchases() {
        return purchaseRecipeDao.findAll();
    }

    public List<User> getTopBuyers() {
        Pageable pageable = PageRequest.of(0, 5);
        return userDao.findTopClientsByPurchases(Role.Client, pageable).getContent();
    }

}
