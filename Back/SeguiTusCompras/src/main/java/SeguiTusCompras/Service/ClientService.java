package SeguiTusCompras.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.Client;
import SeguiTusCompras.persistence.IClientDao;
import SeguiTusCompras.persistence.IProductDAO;
@Service
public class ClientService {
    @Autowired
    IClientDao clientDao;
    @Autowired
    IProductDAO productDao;

    public  Client getClient(String clientName) {
        return Optional.ofNullable(clientDao.getByName(clientName))
        .orElseThrow(() -> new RuntimeException(ServicesErrors.USER_NOT_FOUND.getMessage()));
    }

    public  Client addProductToFavorites(Client client, Product product) {
        client.addToFavorites(product);
        return clientDao.save(client);
    }

    public Client addPurchase(Client client, Product product) {
        client.addToPurchases(product);
        return clientDao.save(client);
    }

    public Client qualifyProduct(Client client, Product product, Integer score, String comment) {
        client.addToQualified(product, score, comment);
        return clientDao.save(client);
    }

}
