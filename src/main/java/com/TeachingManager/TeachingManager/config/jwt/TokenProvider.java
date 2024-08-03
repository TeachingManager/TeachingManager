package com.TeachingManager.TeachingManager.config.jwt;

import com.nimbusds.jose.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtInfo jwtinfo;

    public String generatedToken(User user, Duration expiredAt){
        Date now = new Date();
        return createToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String createToken(Date expiredDate, User user){

        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .setSubject(user.getUsername())
                .claim("id", user.getUsername())
                .signWith(SignatureAlgorithm.HS256, jwtinfo.getSKey())
                .compact();
    }
}
