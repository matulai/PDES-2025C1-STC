package SeguiTusCompras.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.persistence.IProductDao;

@Service
public class ProductService {
    @Autowired
    static
    IProductDao productDao;

    public static Product getProductForClient(String productName) {
        Product product = productDao.getByName(productName);
        if (product == null){
            product = new Product();
            product.setName(productName);
            return productDao.save(product);
        }
        return product;
    }

}
