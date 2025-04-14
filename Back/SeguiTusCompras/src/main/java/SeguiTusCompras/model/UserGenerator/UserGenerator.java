package SeguiTusCompras.model.UserGenerator;
import SeguiTusCompras.model.user.User;
import java.util.HashMap;
import java.util.Map;

public class UserGenerator {
    private final Map<String, IUserFactory> factoryMap = new HashMap<>();
    private static UserGenerator instance;

    public void userFactoryRegistry() {
        factoryMap.put("Client", new ClientFactory());
        factoryMap.put("Admin", new AdminFactory());
    }

    public User generateUser(String role, String name){
        IUserFactory userfactory = this.factoryMap.get(role);
        return userfactory.createUser(name);
    }

    public static UserGenerator getInstance() {
        if (instance == null) {
            instance = new UserGenerator();
            instance.userFactoryRegistry();
        }
        return instance;
    }

}
