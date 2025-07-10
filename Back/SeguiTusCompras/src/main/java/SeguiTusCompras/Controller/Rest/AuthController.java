package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.Utils.ObjectMappers.UserMapper;
import SeguiTusCompras.Controller.dtos.LoginDto;
import SeguiTusCompras.Controller.dtos.RegisterDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.Security.JwtService;
import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.model.user.User;
import jakarta.validation.Valid;
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
    private AuthService authService;

    @CrossOrigin(exposedHeaders = "Authorization")
    @PostMapping(value = "login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto login){
        User user = authService.getUser(login.getName(), login.getPassword());
        String token = generateTokenFor(user.getName());
        UserDto userDto = UserMapper.convertToDto(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(userDto);
    }

    @CrossOrigin(exposedHeaders = "Authorization")
    @PostMapping(value = "register")
    public ResponseEntity<UserDto> register(@Valid  @RequestBody RegisterDto register){
        User persistedUser = authService.createUser(register.getName(), register.getPassword(), register.getRole());
        String token = generateTokenFor(persistedUser.getName());
        UserDto userDto = UserMapper.convertToDto(persistedUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(userDto);
    }

    private String generateTokenFor(String name) {
        return jwtService.getToken(name);
    }
}
