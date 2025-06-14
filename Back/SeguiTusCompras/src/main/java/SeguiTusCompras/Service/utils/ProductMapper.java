package SeguiTusCompras.Service.utils;

import SeguiTusCompras.Controller.Utils.ObjectMappers.QualificationMapper;
import SeguiTusCompras.Controller.dtos.ProductDto;
import SeguiTusCompras.Controller.dtos.PurchaseRecipeDto;
import SeguiTusCompras.Controller.dtos.QualificationDto;
import SeguiTusCompras.Controller.dtos.SimpleProductDto;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.model.Qualification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
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

    public Product toEntityFromDto(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setMlaId(productDto.getMlaId());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        product.setDomainId(productDto.getDomainId());
        product.setDescription(productDto.getDescription());
        return product;
    }

    public List<Product> toEntitiesFromDto(List<ProductDto> productDtos) {
        List<Product> entities = new ArrayList<Product>();
        for(ProductDto productDto: productDtos) {
            entities.add(toEntityFromDto(productDto));
        }
        return entities;
    }

    // Esto hay que refactorizarlo, se repite codigo

    public static ProductDto converToDto(Product product) {
        List<Qualification> qualifications = product.getQualifications();
        List<QualificationDto> qualificationDtos = QualificationMapper.convertListToDto(qualifications);
        ProductDto productDto = new ProductDto(product.getName(), product.getMlaId(), product.getPrice(),
                                product.getImageURL(), product.getDomainId(), product.getDescription(), qualificationDtos);
        return productDto;
    }

    public static List<ProductDto> convertListToDto(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products){
            productDtos.add(converToDto(product));
        }
        return productDtos;
    }

    public static SimpleProductDto converToSimpleDto(Product product) {
        return new SimpleProductDto(product.getName());
    }

    public static List<SimpleProductDto> convertToListSimpleDto(Collection<Product> products){
        List<SimpleProductDto> productDtos = new ArrayList<>();
        for(Product product : products){
            productDtos.add(converToSimpleDto(product));
        }
        return productDtos;
    }

    public static List<PurchaseRecipeDto> convertToListPurchaseRecipeDto(List<PurchaseRecipe> purchaseRecipes) {
        List<PurchaseRecipeDto> purchaseRecipeDtos = new ArrayList<>();
        for(PurchaseRecipe purchaseRecipe: purchaseRecipes) {
            purchaseRecipeDtos.add(converToPurchaseRecipeDto(purchaseRecipe));
        }
        return purchaseRecipeDtos;
    }

    public static PurchaseRecipeDto converToPurchaseRecipeDto(PurchaseRecipe purchaseRecipe) {
        return new PurchaseRecipeDto(purchaseRecipe.getPurchasePrice(),
                purchaseRecipe.getPurchaseDate(),
                convertListToDto(purchaseRecipe.getPurchaseProducts()));
    }
}
