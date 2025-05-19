package SeguiTusCompras.Service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import SeguiTusCompras.model.Comment;
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
public class ClientService {
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

    public  User getClient(String userName) {
        return Optional.ofNullable(userDao.getByName(userName))
        .orElseThrow(() -> new RuntimeException(ServicesErrors.USER_NOT_FOUND.getMessage()));
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
        user.addToQualified(product, score);
        User userWithQualification = userDao.save(user);
        Qualification qualification = qualificationDao.getQualificationFor(user.getName(), product.getName());
        if (comment != null) {
            Comment newComment = userWithQualification.generateComment(comment, qualification);
            commentDao.save(newComment);
        }
        return userWithQualification;
    }

    public Page<User> getAllUserByRole(Role role, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userDao.UsersByRole(role, pageable);
    }

    public List<Qualification> getQualifications(String userName) {
        return userDao.getQualifications(userName);
    }

    public List<Product> getPurchases(String userName) {
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
