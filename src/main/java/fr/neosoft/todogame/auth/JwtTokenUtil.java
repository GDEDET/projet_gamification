package fr.neosoft.todogame.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final String secret = "jklhfliuyfgliuyfgliugluiyglugluigmugmliuglifgliyfkutdut654esea465ze4az354za63qreyqujtdl";

    public String generateToken(String username, Map<String, Object> claims){
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
