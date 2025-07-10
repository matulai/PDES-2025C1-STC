package SeguiTusCompras.Service.Integration;

import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
@Profile("stg")
public class DataSeederStg implements CommandLineRunner {

    private final AuthService authService;
    private final UserService userService;
    private final ProductService productService;

    public DataSeederStg(AuthService authService, UserService userService, ProductService productService) {
        this.authService = authService;
        this.userService = userService;
        this.productService = productService;
    }

    private final String password = "Password123!";

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        /*
        authService.createUser("Yamila", password, "Admin");
        authService.createUser("Lucia", password, "Client");
        authService.createUser("Magali", password, "Client");
        authService.createUser("Rocio", password, "Client");
        authService.createUser("Ernesto", password, "Admin");
        authService.createUser("Carlos", password, "Client");
        authService.createUser("Jorge", password, "Admin");
        authService.createUser("Lucila", password, "Client");
        authService.createUser("Sofia", password, "Admin");
        authService.createUser("Lucas", password, "Client");
        authService.createUser("Branco", password, "Client");

        Product zflip = new Product();
        zflip.setName("Celular Samsung Samsung Zflip");
        zflip.setMlaId("MLA37965307");
        zflip.setPrice(new BigDecimal("1186495.000"));
        zflip.setImageURL("https://http2.mlstatic.com/D_NQ_NP_631627-MLU77166846506_072024-F.jpg");
        zflip.setDomainId("MLA-CELLPHONES");
        zflip.setDescription("Si estás buscando un dispositivo que te permita estar siempre en contacto, este teléfono Samsung Zflip es una excelente opción. Podrás comunicarte de forma inmediata con amigos o con personas de tu familia. Además, si estás trabajando, lograrás una mayor colaboración con tu equipo.");
        zflip.setQualifications(new ArrayList<>());
        zflip = productService.getProduct(zflip);

        Product galaxyS7 = new Product();
        galaxyS7.setName("Celular Samsung Samsung Galaxy S7");
        galaxyS7.setMlaId("MLA37967802");
        galaxyS7.setPrice(new BigDecimal("1186495.000"));
        galaxyS7.setImageURL("https://http2.mlstatic.com/D_NQ_NP_953254-MLU77170226344_072024-F.jpg");
        galaxyS7.setDomainId("MLA-CELLPHONES");
        galaxyS7.setDescription("Si estás buscando un dispositivo que te permita estar siempre en contacto, este teléfono Samsung Galaxy S7 es una excelente opción. Podrás comunicarte de forma inmediata con amigos o con personas de tu familia. Además, si estás trabajando, lograrás una mayor colaboración con tu equipo. \\n \\n Tecnología de doble SIM \\n Con tu sistema de doble SIM, sentirás la comodidad de tener dos líneas en un solo dispositivo. Toma tu información personal y laboral, instala aplicaciones independientes y elige una línea u otra según tus necesidades.\"");
        galaxyS7.setQualifications(new ArrayList<>());
        galaxyS7 = productService.getProduct(galaxyS7);

        Product gt9070 = new Product();
        gt9070.setName("Celular Samsung Samsung Gt9070");
        gt9070.setMlaId("MLA37964944");
        gt9070.setPrice(new BigDecimal("1186495.000"));
        gt9070.setImageURL("https://http2.mlstatic.com/D_NQ_NP_712348-MLU77166591444_072024-F.jpg");
        gt9070.setDomainId("MLA-CELLPHONES");
        gt9070.setDescription("Si buscas un dispositivo que te permita estar siempre en contacto, este teléfono Samsung gt9070 es una excelente opción. Podrás comunicarte de forma inmediata con amigos o con personas de tu familia. Además, si estás trabajando, lograrás una mayor colaboración con tu equipo.");
        gt9070.setQualifications(new ArrayList<>());
        gt9070 = productService.getProduct(gt9070);

        Product rugby = new Product();
        rugby.setName("Samsung Rugby");
        rugby.setMlaId("MLA5006276");
        rugby.setPrice(new BigDecimal("1186495.000"));
        rugby.setImageURL("https://http2.mlstatic.com/D_NQ_NP_228905-MLA25117327673_102016-F.jpg");
        rugby.setDomainId("MLA-CELLPHONES");
        rugby.setDescription("Si buscás un dispositivo que te permita estar en contacto siempre, este teléfono Rugby es una opción excelente. Vas a poder comunicarte de manera inmediata con amigas y amigos, o con las personas de tu familia. Y además, si estás trabajando, vas a alcanzar una mayor colaboración con tu equipo.");
        rugby.setQualifications(new ArrayList<>());
        rugby = productService.getProduct(rugby);


        Product muse = new Product();
        muse.setName("Samsung Muse");
        muse.setMlaId("MLA6000449");
        muse.setPrice(new BigDecimal("1186495.000"));
        muse.setImageURL("https://http2.mlstatic.com/D_NQ_NP_625905-MLA25114436788_102016-F.jpg");
        muse.setDomainId("MLA-CELLPHONES");
        muse.setDescription("Si buscás un dispositivo que te permita estar en contacto siempre, este teléfono Muse es una opción excelente. Vas a poder comunicarte de manera inmediata con amigas y amigos, o con las personas de tu familia. Y además, si estás trabajando, vas a alcanzar una mayor colaboración con tu equipo.");
        muse.setQualifications(new ArrayList<>());
        muse = productService.getProduct(muse);

        Product miCoach = new Product();
        miCoach.setName("Samsung MiCoach");
        miCoach.setMlaId("MLA986211");
        miCoach.setPrice(new BigDecimal("1186495.000"));
        miCoach.setImageURL("https://http2.mlstatic.com/D_NQ_NP_641215-MLA25138603589_112016-F.jpg");
        miCoach.setDomainId("MLA-CELLPHONES");
        miCoach.setDescription("Si buscás un dispositivo que te permita estar en contacto siempre, este teléfono MiCoach es una opción excelente. Vas a poder comunicarte de manera inmediata con amigas y amigos, o con las personas de tu familia. Y además, si estás trabajando, vas a alcanzar una mayor colaboración con tu equipo.");
        miCoach.setQualifications(new ArrayList<>());
        miCoach = productService.getProduct(miCoach);



        User lucia = userService.getUser("Lucia");
        User magali = userService.getUser("Magali");
        User branco = userService.getUser("Branco");

        userService.addToCart(lucia, zflip);
        userService.addToCart(lucia, galaxyS7);
        userService.addToCart(lucia, rugby);
        userService.addToCart(lucia, miCoach);
        userService.addToCart(lucia, muse);
        userService.addPurchases(lucia);

        userService.addToCart(magali, gt9070);
        userService.addPurchases(magali);

        userService.addToCart(branco, zflip);
        userService.addToCart(branco, miCoach);
        userService.addPurchases(branco);

        userService.addProductToFavorites(lucia, gt9070);

        userService.addProductToFavorites(magali, zflip);
        userService.addProductToFavorites(magali, rugby);

        userService.addProductToFavorites(branco, rugby);
        userService.addProductToFavorites(branco, gt9070);
        userService.addProductToFavorites(branco, miCoach);

        userService.qualifyProduct(lucia, zflip, 5, "¡Increíble rendimiento para juegos! La mejor compra que he hecho.");
        userService.qualifyProduct(lucia, galaxyS7, 4, "Muy bueno y cómodo para escribir, aunque un poco ruidoso.");
    **/
    }
}