package SeguiTusCompras.Security;

import SeguiTusCompras.model.user.User;
import SeguiTusCompras.persistence.IUserDao;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    IUserDao userDao;
    @Value("${jwt.key}")
    private String key;

    public String getToken(String name) {
        User user = userDao.getByName(name);
        return getNewToken(new HashMap<>(), user);
    }

    private String getNewToken(Map<String, Object> extraClaims, User user) {
        extraClaims.put("role", user.getRole().toString());  // Agregar el rol al claim
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)// Se asegura de usar una clave válida.
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

