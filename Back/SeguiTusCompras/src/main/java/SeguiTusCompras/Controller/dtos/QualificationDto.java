package SeguiTusCompras.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDto {
    private String userName;
    private ProductDto productDto;
    private Integer score;
}
