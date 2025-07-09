package SeguiTusCompras.Service.utils;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "requests-by-country")
public class RequestsByCountryEndpoint {

    private final GeoIPService geoIPService;

    public RequestsByCountryEndpoint(GeoIPService geoIPService) {
        this.geoIPService = geoIPService;
    }

    @ReadOperation
    public Map<String, Integer> requestsByCountry() {
        return geoIPService.getRequestCounts();
    }
}

