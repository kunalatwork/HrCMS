package com.HrCMS.JwtServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtHelper {

    private static final String JWT_SECRET="hgtyqiuhcqsdgrgeRERgwErgwerwERWerwerywerAISFYaiufASF3t3rutWR3gwrtuyafawetr8awetaygf8ag48atwytsegdruajwytseudjrytfgawuzjsehdtrgfuawzhsetgdruywErytquhhiajksiausglkahsdahskghshdkalljgasdkjahghakjsglsdjfhkadkgakdg";
    private static final String JWT_HEADER="Authorization";

 //   private final SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());


    private Key getsignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getsignKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }


    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(username, claims);
    }

    public String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date((System.currentTimeMillis() + 1000 * 60 * 60 * 10)))
                .signWith(getsignKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date getExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }


//    public Claims getEmailFromToken(String token) {
//        return  Jwts.parserBuilder().setSigningKey(getsignKey).build().parseClaimsJwt(token).getBody();
//    }
}
