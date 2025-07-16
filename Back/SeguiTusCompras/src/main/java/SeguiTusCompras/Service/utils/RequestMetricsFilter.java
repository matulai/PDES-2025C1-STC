package SeguiTusCompras.Service.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestMetricsFilter extends OncePerRequestFilter {

    private final GeoIPService geoIPService;

    public RequestMetricsFilter(GeoIPService geoIPService) {
        this.geoIPService = geoIPService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Obtenemos la IP correcta del cliente
            String clientIp = getClientIpAddress(request);

            // Usamos tu servicio para obtener el país
            String country = geoIPService.getCountryForIp(clientIp);

            // Incrementamos el contador para Prometheus/Grafana
            geoIPService.incrementRequestCount(country);
        } catch (Exception e) {
            // Si algo falla, no queremos detener la solicitud.
            // Registramos el error y continuamos.
            logger.error("Could not process request for metrics", e);
            // Igualmente incrementamos con "Unknown" para saber que hubo un intento fallido.
            geoIPService.incrementRequestCount("Unknown");
        }


        // Continuamos con el resto de los filtros y el controlador
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae la IP del cliente del encabezado X-Forwarded-For o, si no está presente,
     * de la dirección remota de la solicitud.
     * @param request El objeto de la solicitud HTTP.
     * @return La dirección IP del cliente.
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || "unknown".equalsIgnoreCase(xfHeader)) {
            // Si el header no está, usamos la IP de la conexión directa (fallback)
            return request.getRemoteAddr();
        }
        // El header X-Forwarded-For puede contener una lista de IPs (cliente, proxy1, proxy2...).
        // La primera IP de la lista es siempre la del cliente original.
        return xfHeader.split(",")[0].trim();
    }
}