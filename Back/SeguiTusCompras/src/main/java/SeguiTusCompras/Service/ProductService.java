package SeguiTusCompras.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.report.ProductReport;
import SeguiTusCompras.persistence.IProductDao;
import SeguiTusCompras.persistence.report.IProductReport;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {
    @Autowired
    IProductDao productDao;
    @Autowired
    IProductReport productReportDao;

    public Product getProduct(Product product) {
        Product persistedProduct = productDao.getByName(product.getName());
        if (persistedProduct == null){
            Product newProduct = productDao.save(product);
            ProductReport productReport = new ProductReport();
            productReport.setProduct(newProduct);
            ProductReport report = productReportDao.save(productReport);
            newProduct.setProductReport(report);
            return productDao.save(newProduct);
        }
        return persistedProduct;    
    }

    public Product getProductByName(String productName) {
        return productDao.getByName(productName);
    }

    public Product getProductByMlaId(String id) { return productDao.getByMlaId(id);}

    public List<Product> getTopSellingProducts() {
        Pageable topFive = PageRequest.of(0, 5); 
        return productReportDao.getTopSellingProducts(topFive);
    }

    public List<Product> getTopFavoriteProducts() {
        Pageable topFive = PageRequest.of(0, 5); 
        return productReportDao.getTopFavoriteProducts(topFive);
    }

}
