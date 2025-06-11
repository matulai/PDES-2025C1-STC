package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.model.user.User;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserDto> getUserMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authService.getUserByName(username);
        UserDto userDto = UserMapper.convertToDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping(value = "favs")
    public String getFavs(){
        return "asd";
    }
}
