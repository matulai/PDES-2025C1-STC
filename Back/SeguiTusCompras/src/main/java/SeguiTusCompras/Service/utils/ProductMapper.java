package SeguiTusCompras.Service.utils;

import SeguiTusCompras.model.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public Product toEntity(ProductAPIExternal productAPIExternal) {
        Product product = new Product();
        product.setMlaId(productAPIExternal.id);
        product.setName(productAPIExternal.name);
        product.setDomainId(productAPIExternal.domain_id);
        if (productAPIExternal.short_description != null
                && productAPIExternal.short_description.content != null) {
            product.setDescription(productAPIExternal.short_description.content);
        }

        if (productAPIExternal.pictures != null && !productAPIExternal.pictures.isEmpty()) {
            product.setImageURL(productAPIExternal.pictures.getFirst().url);
        }

        return product;
    }

    public List<Product> toEntities(List<ProductAPIExternal> productAPIExternalList) {
        if (productAPIExternalList == null) {
            return Collections.emptyList();
        }

        return productAPIExternalList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
