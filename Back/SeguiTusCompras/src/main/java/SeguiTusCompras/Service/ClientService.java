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
    IClientDao clientDao;
    @Autowired
    IProductDao productDao;

    public  Client getClient(String clientName) {
        return Optional.ofNullable(clientDao.getByName(clientName))
        .orElseThrow(() -> new RuntimeException(ServicesErrors.USER_NOT_FOUND.getMessage()));
    }

    public  Client addProductToFavorites(Client client, Product product) {
        client.addToFavs(product);
        return clientDao.save(client);
    }

    public Client addPurchase(Client client, Product product, Integer numberOfUnits) {
        client.purchase(product, numberOfUnits);
        return clientDao.save(client);
    }

    public Client qualifyProduct(Client client, Product product, Integer score) {
        client.qualifyPorduct(product, score);
        return clientDao.save(client);
    }

}
