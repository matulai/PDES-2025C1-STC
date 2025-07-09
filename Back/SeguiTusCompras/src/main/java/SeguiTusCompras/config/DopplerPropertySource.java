package SeguiTusCompras.config;

import org.apache.logging.log4j.util.InternalException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.Ordered;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
public class DopplerPropertySource implements EnvironmentPostProcessor, Ordered {

    // Le damos una prioridad alta para que se ejecute pronto.
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        boolean isStaging = Arrays.asList(environment.getActiveProfiles()).contains("stg");

        if (isStaging) {
            return;
        }

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
                throw new InternalException("Fallo al obtener credenciales");
            }

            JSONObject secretsJson = new JSONObject(response.body());
            Map<String, Object> dopplerSecrets = new HashMap<>();
            for (String key : secretsJson.keySet()) {
                dopplerSecrets.put(key, secretsJson.getString(key));
            }

            MapPropertySource propertySource = new MapPropertySource("doppler-secrets", dopplerSecrets);
            environment.getPropertySources().addFirst(propertySource);


        } catch (Exception e) {
            throw new InternalException("Fallo al cargar credenciales");
        }
    }
}
