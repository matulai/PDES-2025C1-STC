package SeguiTusCompras.Security;

import SeguiTusCompras.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {

    private static final String key = "verysecret";

    public String getToken(User user) {
        return getNewToken(new HashMap<>(), user);
    }

    private String getNewToken(Map<String, Object> extraClaims, User user) {
            return Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(user.getName())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                    .signWith(this.getKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    private Key getKey() {
        byte[] keyBites= Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBites);
    }
}
