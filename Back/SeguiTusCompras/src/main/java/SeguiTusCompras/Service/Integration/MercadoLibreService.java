package SeguiTusCompras.Service.Integration;

import SeguiTusCompras.Service.utils.ExternalTokenManager;
import SeguiTusCompras.Service.utils.ProductAPIExternal;
import SeguiTusCompras.Service.utils.ProductMapper;
import SeguiTusCompras.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MercadoLibreService {

    private final ExternalTokenManager externalTokenManager;
    private final HttpClient httpClient;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;

    public MercadoLibreService(ExternalTokenManager externalTokenManager, ProductMapper productMapper, ObjectMapper objectMapper) {
        this.externalTokenManager = externalTokenManager;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        this.productMapper = productMapper;
    }

    public List<Product> searchProducts(String text) {
        String endpoint = String.format("https://api.mercadolibre.com/products/search?status=active&site_id=MLA&q=%s",
                URLEncoder.encode(text, StandardCharsets.UTF_8));
        String json = sendGetRequest(endpoint);
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode resultsNode = root.get("results");
            List<ProductAPIExternal> productAPIExternalList = objectMapper.readerForListOf(ProductAPIExternal.class).readValue(resultsNode);
            return productMapper.toEntities(productAPIExternalList);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing search response", e);
        }
    }

    public Product getProductById(String id) {
        String endpoint = String.format("https://api.mercadolibre.com/products/%s",
                URLEncoder.encode(id, StandardCharsets.UTF_8));
        String json = sendGetRequest(endpoint);
        try {
            ProductAPIExternal productAPIExternal = objectMapper.readValue(json, ProductAPIExternal.class);
            return productMapper.toEntity(productAPIExternal);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing product by id", e);
        }
    }

    private String sendGetRequest(String url) {
        try {
            String token = externalTokenManager.getAccessToken();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Error petition on : " + response.statusCode() + " - " + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error on sendGetRequest", e);
        }
    }

}
