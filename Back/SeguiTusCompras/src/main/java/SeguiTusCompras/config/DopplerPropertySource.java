package SeguiTusCompras.config;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

public class DopplerPropertySource implements EnvironmentPostProcessor, Ordered {
    
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String dopplerToken = System.getenv("DOPPLER_TOKEN");

        if (dopplerToken == null || dopplerToken.isBlank()) {
            return;
        }

        try {
            String encodedAuth = Base64.getEncoder().encodeToString((dopplerToken + ":").getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.doppler.com/v3/configs/config/secrets/download?format=json"))
                    .header("Authorization", "Basic " + encodedAuth)
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Failed to fetch secrets from Doppler. Status code: "
                        + response.statusCode() + ", Body: " + response.body());
            }

            JSONObject secretsJson = new JSONObject(response.body());
            Map<String, Object> dopplerSecrets = secretsJson.toMap();

            MapPropertySource propertySource = new MapPropertySource("doppler-secrets", dopplerSecrets);
            environment.getPropertySources().addFirst(propertySource);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Doppler secret loading was interrupted", e);

        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Failed to initialize and load secrets from Doppler due to a network or configuration error", e);
        }
    }
}