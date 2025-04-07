package SeguiTusCompras.Controller.Rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping(value = "string")
    public String getString(){
        return "Este endpoint es solo para admin";
    }
}
