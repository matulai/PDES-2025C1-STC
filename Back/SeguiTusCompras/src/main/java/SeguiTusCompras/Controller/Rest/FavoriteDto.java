package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
    private String userName;
    private ProductDto productDto;
}
