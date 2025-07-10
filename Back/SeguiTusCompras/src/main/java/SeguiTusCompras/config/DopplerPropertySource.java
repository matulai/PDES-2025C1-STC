package SeguiTusCompras.config;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.Ordered;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Map;

public class DopplerPropertySource implements EnvironmentPostProcessor, Ordered {

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
            HttpClient client = HttpClient.newHttpClient();
            String encodedAuth = Base64.getEncoder().encodeToString((dopplerToken + ":").getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.doppler.com/v3/configs/config/secrets/download?format=json"))
                    .header("Authorization", "Basic " + encodedAuth)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Fallo al obtener credenciales de Doppler. Status code: " + response.statusCode());
            }

            JSONObject secretsJson = new JSONObject(response.body());

            // --- CODE IMPROVEMENT ---
            // Replace the manual loop with the more robust and cleaner toMap() method.
            // This avoids the call to keySet() altogether, fixing the NoSuchMethodError.
            Map<String, Object> dopplerSecrets = secretsJson.toMap();
            // --- END OF IMPROVEMENT ---

            MapPropertySource propertySource = new MapPropertySource("doppler-secrets", dopplerSecrets);
            environment.getPropertySources().addFirst(propertySource);

        } catch (Exception e) {
            // Envuelve la excepción original para no perder la causa raíz.
            throw new IllegalStateException("Fallo al cargar credenciales de Doppler", e);
        }
    }
}