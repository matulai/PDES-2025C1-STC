package SeguiTusCompras.Controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class LoginDto {
    private String userName;
    private String passowrd;
}
