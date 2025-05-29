package SeguiTusCompras.Service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import SeguiTusCompras.Error.ErrorMessages;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.ICommentDao;
import SeguiTusCompras.persistence.IProductDao;
import SeguiTusCompras.persistence.IQualificationDAO;
import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.persistence.report.IUserReportDao;
@Service
public class UserService {
    @Autowired
    IUserDao userDao;
    @Autowired
    IProductDao productDao;
    @Autowired
    IQualificationDAO qualificationDao;
    @Autowired
    ICommentDao commentDao;
    @Autowired
    IUserReportDao userReportDao;

    public  User getUser(String userName) {

        return Optional.ofNullable(userDao.getByName(userName))
        .orElseThrow(() -> new RuntimeException(ErrorMessages.USER_NOT_FOUND.getMessage()));
    }

    public  User addProductToFavorites(User client, Product product) {
        client.addToFavorites(product);
        return userDao.save(client);
    }

    public User addPurchase(User user, Product product) {
        user.addToPurchases(product);
        return userDao.save(user);
    }

    public User qualifyProduct(User user, Product product, Integer score, String comment) {
        checkIfScoreIsValid(score);
        user.qualifyProduct(product, score);
        User userWithQualification = userDao.save(user);
        Qualification qualification = userWithQualification.getQualificationForProduct(product);
        if (comment != null && qualification != null) {
            userWithQualification.generateComment(comment, qualification);
            qualificationDao.save(qualification);
            User userWithComment = userDao.save(user);
            return userWithComment;
        }
        return userWithQualification;
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
