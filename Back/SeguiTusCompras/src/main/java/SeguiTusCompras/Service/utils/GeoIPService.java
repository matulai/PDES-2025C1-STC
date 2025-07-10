package SeguiTusCompras.Service.utils;

import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class GeoIPService {

    private final Map<String, AtomicInteger> countryRequestCounts = new ConcurrentHashMap<>();

    public String getCountryForIp(String ip) {
        try {
            URL url = new URL("http://ip-api.com/json/" + ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String response = in.lines().collect(Collectors.joining());
                JSONObject json = new JSONObject(response);
                return json.optString("country", "Unknown");
            }
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public void incrementRequestCount(String country) {
        countryRequestCounts.computeIfAbsent(country, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public Map<String, Integer> getRequestCounts() {
        return countryRequestCounts.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}

