package SeguiTusCompras.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRecipeDto {
    private BigDecimal purchasePrice;
    private LocalDateTime purchaseDate;
    private List<ProductDto> purchaseProducts;
}
