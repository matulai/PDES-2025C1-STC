package SeguiTusCompras.Service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeoIPService {
    
    private final MeterRegistry meterRegistry;

    public GeoIPService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

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
        meterRegistry.counter("requests.by.country", "country", country).increment();
    }

    public Map<String, Integer> getRequestCounts() {
        return meterRegistry.find("requests.by.country").counters().stream()
                .collect(Collectors.toMap(
                        c -> c.getId().getTag("country"),
                        c -> (int) c.count()
                ));
    }
}

