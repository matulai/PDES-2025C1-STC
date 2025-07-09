package SeguiTusCompras.Service.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final GeoIPService geoIPService;

    public RequestLoggingFilter(GeoIPService geoIPService) {
        this.geoIPService = geoIPService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String country = geoIPService.getCountryForIp(ip);
        geoIPService.incrementRequestCount(country);

        filterChain.doFilter(request, response);
    }
}

