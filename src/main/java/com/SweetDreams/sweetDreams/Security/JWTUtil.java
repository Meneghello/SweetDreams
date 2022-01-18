package com.SweetDreams.sweetDreams.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String segredo;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes())
                .compact();
    }

    public boolean tokenValido(String token){
        Claims claims = getClaims(token);
        if (claims != null){
            String username = claims.getSubject();
            Date expDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (username!=null && expDate!=null && now.before(expDate)){
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token){
        Claims claims = getClaims(token);
        if (claims!=null){
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
        }
        catch (Exception e){
            return null;
        }

    }
}
