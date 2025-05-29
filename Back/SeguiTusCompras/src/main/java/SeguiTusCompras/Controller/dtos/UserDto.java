package SeguiTusCompras.Controller.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String role;
    private List<SimpleProductDto> favorites;
    private List<SimpleProductDto> purchases;
    private List<QualificationDto> qualifications;
}
