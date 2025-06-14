package SeguiTusCompras.Service;

import java.util.List;
import java.util.Optional;

import SeguiTusCompras.model.PurchaseRecipe;
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
import SeguiTusCompras.persistence.report.IUserReportDao;
@Service
public class UserService {

    private final IUserDao userDao;
    private final IQualificationDAO qualificationDao;
    private final IUserReportDao userReportDao;
    private final IPurchaseRecipeDao purchaseRecipeDao;

    public UserService(IUserDao userDao, IQualificationDAO qualificationDao,
                       IUserReportDao userReportDao, IPurchaseRecipeDao purchaseRecipeDao) {
        this.userDao = userDao;
        this.qualificationDao = qualificationDao;
        this.userReportDao = userReportDao;
        this.purchaseRecipeDao = purchaseRecipeDao;
    }

    public  User getUser(String userName) {

        return Optional.ofNullable(userDao.getByName(userName))
        .orElseThrow(() -> new RuntimeException(ErrorMessages.USER_NOT_FOUND.getMessage()));
    }

    public  User addProductToFavorites(User client, Product product) {
        if (client.getFavorites().contains(product)) {
            client.deleteFromFavorites(product);
        } else {
            client.addToFavorites(product);
        }
        return userDao.save(client);
    }

    public void addToCart(User user, Product product) {
        user.addToCart(product);
        userDao.save(user);
    }

    public void addPurchases(User user) {
        PurchaseRecipe purchaseRecipe = purchaseRecipeDao.save(new PurchaseRecipe(user.getCart(), user));
        user.addToPurchase(purchaseRecipe);
        user.cleanCart();
        userDao.save(user);
    }

    public User qualifyProduct(User user, Product product, Integer score, String comment) {
        checkIfScoreIsValid(score);
        user.qualifyProduct(product, score, comment);
        return userDao.save(user);
    }

    private void checkIfScoreIsValid(Integer score) {
        if(score < 1 || score > 5) {
            throw new RuntimeException(ErrorMessages.INVALID_SCORE.getMessage());
        }
    }

    public Page<User> getAllUserByRole(Role role, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userDao.UsersByRole(role, pageable);
    }

    public List<Qualification> getQualificationsMadeByUser(String userName) {
        return userDao.getQualifications(userName);
    }

    public List<Product> getPurchasesFromUser(String userName) {
        return userDao.getPurchases(userName);
    }

    public List<Product> getFavorites(String userName) {
        return userDao.getFavorites(userName);
    }

    public List<User> getTopBuyers() {
        Pageable topFive = PageRequest.of(0, 5); 
        return userReportDao.getTopBuyers(topFive);
    }

   
}
