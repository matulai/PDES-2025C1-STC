package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.Utils.Validators.AuthValidator;
import SeguiTusCompras.Controller.dtos.ClientDto;
import SeguiTusCompras.Controller.dtos.LoginDto;
import SeguiTusCompras.Controller.dtos.RegisterDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.Security.JwtService;
import SeguiTusCompras.Security.UserSecurity;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private IUserSecurity userSecurity;

    @PostMapping(value = "login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto login){
        User user = userService.getUser(login.getName(), login.getPassword());
        String token = generateTokenFor(user.getName());
        UserDto userDto = UserMapper.convertToDto(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(userDto);
    }
    @PostMapping(value = "register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto register){
        AuthValidator.getInstance().ValidateRegister(register);
        User newUser = userService.createUser(register.getName(), register.getPassword(), register.getRole());
        String token = generateTokenFor(newUser.getName());
        UserDto userDto = UserMapper.convertToDto(newUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(userDto);
    }

    private String generateTokenFor(String name) {
        UserSecurity userSec = userSecurity.getByName(name);
        return jwtService.getToken(userSec);
    }
}
