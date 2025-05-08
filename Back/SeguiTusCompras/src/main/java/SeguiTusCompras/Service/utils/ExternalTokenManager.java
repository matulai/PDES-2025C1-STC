package SeguiTusCompras.Service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ExternalTokenManager {
    private String accessToken;
    private Instant expiryTime;
    private final ReentrantLock lock = new ReentrantLock();

    @Value("${api.external.client.id}")
    private String clientId;
    @Value("${api.external.client.secret}")
    private String clientSecret;
    @Value("${api.external.refresh.token}")
    private String refreshToken;

    public String getAccessToken() {
        if (isTokenExpired()) {
            refreshTokenIfNeeded();
        }
        return accessToken;
    }

    private boolean isTokenExpired() {
        return expiryTime == null || Instant.now().isAfter(expiryTime.minusSeconds(30)); // Security margin
    }

    private void refreshTokenIfNeeded() {
        if (lock.tryLock()) {
            try {
                // Another verification of token
                if (isTokenExpired()) {
                    // Call to refresh the token
                    var newToken = callExternalApiToRefresh();
                    this.accessToken = newToken.getAccessToken();
                    this.expiryTime = Instant.now().plusSeconds(newToken.getExpiresIn());
                }
            } finally {
                lock.unlock();
            }
        } else {
            // Wait till the refresh on the other thread ends.
            try {
                Thread.sleep(5000); // maybe should I use wait/notify
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private ExternalTokenResponse callExternalApiToRefresh() {
        String url = "https://api.mercadolibre.com/oauth/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExternalTokenResponse> response = restTemplate.postForEntity(
                url, request, ExternalTokenResponse.class);

        return response.getBody();
    }
}
