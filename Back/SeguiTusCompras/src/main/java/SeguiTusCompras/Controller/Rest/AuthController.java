package SeguiTusCompras.Controller.Rest;

import SeguiTusCompras.Controller.dtos.ClientDto;
import SeguiTusCompras.Controller.dtos.LoginDto;
import SeguiTusCompras.Controller.dtos.RegisterDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.Security.JwtService;
import SeguiTusCompras.model.user.User;
import SeguiTusCompras.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { // hay que ver el token para autenticarse
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto login){
        //TODO ESTO NO ESTA HECHO
        ClientDto client = new ClientDto("a", "a", "a"); // devolver el user
        User user = userService.getUser(login.getUserName());
        String token = jwtService.getToken(user);
        return ResponseEntity.ok().body(client);
    }
    @PostMapping(value = "register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto register){
        ClientDto client = new ClientDto("a", "a", "a"); // devolver el user
        User newUser = userService.createUser(register.getName(), register.getPassword(), register.getRole());
        String token = jwtService.getToken(newUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(client);
    }
}
