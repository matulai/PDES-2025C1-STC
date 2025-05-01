package SeguiTusCompras.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.persistence.IClientDao;
import SeguiTusCompras.persistence.IProductDao;
@Service
public class ClientService {
    @Autowired
    static
    IClientDao clientDao;
    @Autowired
    static
    IProductDao productDao;

    public static Client getClient(String clientName) {
        return Optional.ofNullable(clientDao.getByName(clientName))
        .orElseThrow(() -> new RuntimeException(ServicesErrors.USER_NOT_FOUND.getMessage()));
    }

    public static Client addProductToFavorites(Client client, Product product) {
        client.addToFavs(product);
        return clientDao.save(client);
    }

    public Client addPurchase(Client client, Product product) {
        client.purchase(product);
        return clientDao.save(client);
    }

    public Client qualifyProduct(Client client, Product product, Integer score) {
        client.qualifyPorduct(product, score);
        productDao.save(product);
        return clientDao.save(client);
    }



}
