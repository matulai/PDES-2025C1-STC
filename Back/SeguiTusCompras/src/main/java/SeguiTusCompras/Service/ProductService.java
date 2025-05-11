package SeguiTusCompras.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.persistence.IProductDao;

@Service
public class ProductService {
    @Autowired
    IProductDao productDao;

    public Product getProductForClient(Product product) {
        Product persistedProduct = productDao.getByName(product.getName());
        if (persistedProduct == null){
            return productDao.save(product);
        }
        return product;
    }

    public Product getProduct(String productName) {
        return productDao.getByName(productName);
    }

}
