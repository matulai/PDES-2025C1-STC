package SeguiTusCompras.Controller.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageDto {
    private List<SimpleUserDto> users;
    private boolean hasPrevious;
    private boolean hasNext;
}
