package com.TeachingManager.TeachingManager.config.jwt;

import com.TeachingManager.TeachingManager.domain.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtInfo jwtinfo;

    // 외부에서 호출하기 위한 공용메서드
    public String createAccessToken(CustomUser user, Duration expiredAt){
        Date now = new Date();
        return createToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 외부에서 refresh 토큰 호출 메서드
    public String createRefreshToken(CustomUser user, Duration expiredAt){
        Date now = new Date();
        return createToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

// private 을 이용하여 접근제한?
    private String createToken(Date expiredDate, CustomUser user){

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .setSubject(user.getEmail())
                .claim("id", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtinfo.getSKey())
                .compact();
    }

    // 토큰 유효성 검사
    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtinfo.getSKey()).parseClaimsJws(token);
            return true;
        } catch (Exception notValid) {
            return false;
        }
    }

    // 토큰을 이용하여 인증 정보 가져오기
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    public long getUserId(String token) {
        Claims claims = getClaims(token);
        System.out.println("tokenProvider 의 getuserid 의 claims = " + claims);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtinfo.getSKey())
                .parseClaimsJws(token)
                .getBody();
    }


}
