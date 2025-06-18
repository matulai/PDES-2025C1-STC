package SeguiTusCompras.Service;

import java.util.List;

import SeguiTusCompras.Controller.dtos.PaginationElementDto;
import SeguiTusCompras.Controller.dtos.ProductDto;
import SeguiTusCompras.Service.utils.Pagination;
import SeguiTusCompras.Service.utils.ProductMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
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

    public PaginationElementDto<ProductDto> getAllFavoritesProducts(int page, int size) {
        Page<Product> productPage = productDao.findAllFavoriteProductsPage(PageRequest.of(page - 1, size));
        List<ProductDto> productDtoList = productPage.getContent()
                .stream()
                .map(ProductMapper::converToDto)
                .toList();
        Pagination pagination = new Pagination(page, size, productPage.getTotalElements());
        return new PaginationElementDto<>(productDtoList, pagination);
    }

    public List<Product> getTopSellingProducts() {
        Pageable topFive = PageRequest.of(0, 5);
        return productDao.findTopSellingProducts(topFive).getContent();
    }

    public List<Product> getTopFavoriteProducts() {
        Pageable topFive = PageRequest.of(0, 5); 
        return productDao.findTopProductsFavoritesOfAllUsers(topFive).getContent();
    }
}
