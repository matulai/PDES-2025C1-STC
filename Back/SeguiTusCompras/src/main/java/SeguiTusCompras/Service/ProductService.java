package SeguiTusCompras.Service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.persistence.IProductDao;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {
    private final IProductDao productDao;

    public ProductService(IProductDao productDao) {
        this.productDao = productDao;
    }

    public Product getProduct(Product product) {
        Product persistedProduct = productDao.getByName(product.getName());
        if (persistedProduct == null){
            return productDao.save(product);
        }
        return persistedProduct;    
    }

    public Product getProductByName(String productName) {
        return productDao.getByName(productName);
    }

    public Product getProductByMlaId(String id) { return productDao.findByMlaId(id).orElse(null);}

    public List<Product> getAllFavoritesProducts() {
        return productDao.findAllFavoriteProductsOfAllUsers();
    }

    public List<Product> getTopSellingProducts() {
        Pageable topFive = PageRequest.of(0, 5); 
        return productDao.findTopPurchasesProductsOfAllUsers(topFive);
    }

    public List<Product> getTopFavoriteProducts() {
        Pageable topFive = PageRequest.of(0, 5); 
        return productDao.findTopProductsFavoritesOfAllUsers(topFive);
    }
}
