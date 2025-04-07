package SeguiTusCompras.Security;

import SeguiTusCompras.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String key = "VGhpcyBpcyBhIHNlY3VyZSBzZWNyZXQga2V5VGhpcyBpcyBhIHNlY3VyZSBzZWNyZXQga2V5Cg==";

    public String getToken(UserSecurity user) {
        return getNewToken(new HashMap<>(), user);
    }

    private String getNewToken(Map<String, Object> extraClaims, UserSecurity user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUserModel().getName())
                .setSubject(user.getRole().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)// Se asegura de usar una clave válida.
                .compact();
    }

    /**
     * La versión antigua de este método decodificaba la clave en Base64 pero no cumplía con el tamaño necesario.
     * private Key getKey() {
     *     byte[] keyBites = Decoders.BASE64.decode(key);
     *     return Keys.hmacShaKeyFor(keyBites);
     * }
     */

    private Key getKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

