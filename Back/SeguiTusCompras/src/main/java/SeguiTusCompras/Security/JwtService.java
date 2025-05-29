package SeguiTusCompras.Security;

import SeguiTusCompras.persistence.IUserDao;
import SeguiTusCompras.model.user.User;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import java.security.Key;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String key;

    private final IUserDao userDao;

    public JwtService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public String getToken(String name) {
        User user = userDao.getByName(name);
        return getNewToken(new HashMap<>(), user);
    }

    private String getNewToken(Map<String, Object> extraClaims, User user) {
        extraClaims.put("role", user.getRole().toString());  // Add rol to the claim
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

