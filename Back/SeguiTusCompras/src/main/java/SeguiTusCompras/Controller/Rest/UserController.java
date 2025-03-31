package SeguiTusCompras.Controller.Rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping(value = "favs")
    public String getFavs(){
        return "asd";
    }
}
