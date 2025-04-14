package SeguiTusCompras.Controller.dtos;

import lombok.Getter;

@Getter
public abstract class UserDto {
    private String name;

    public UserDto(String name) {this.name = name;}
}
