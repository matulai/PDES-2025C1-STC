package SeguiTusCompras.Controller.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String mlaId;
    private BigDecimal price;
    private String imageURL;
    private String domainId;
    private String description;
    private List<QualificationDto> qualifications;
}
