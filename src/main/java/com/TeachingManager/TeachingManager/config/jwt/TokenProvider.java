package com.TeachingManager.TeachingManager.config.jwt;

import com.TeachingManager.TeachingManager.Repository.User.RefreshTokenRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtInfo jwtinfo;
    private final RefreshTokenRepository refreshTokenRepo;

    // 외부에서 호출하기 위한 공용메서드
    public String createAccessToken(CustomUser user, Duration expiredAt){
        Date now = new Date();
        return createToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 외부에서 refresh 토큰 호출 메서드
    public String createRefreshToken(CustomUser user, Duration expiredAt){
        Date now = new Date();
        Date expired_time = new Date(now.getTime() + expiredAt.toMillis());
        String token = createToken(expired_time, user);


        RefreshToken newRefreshToken = new RefreshToken(user.getPk(), token, expired_time);
        // 이미 refreshToken 이 존재하는지 체크하고 있다면 업데이트, 아니라면 저장
        Optional<RefreshToken> refreshToken = refreshTokenRepo.findByUserId(user.getPk());
        if(refreshToken.isPresent()) {
            refreshToken.get().update(String.valueOf(newRefreshToken));
        }
        else {
            refreshTokenRepo.save(newRefreshToken);
        }

        return token;
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
                .claim("id", user.getPk())
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, jwtinfo.getSKey())
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

        List<String> roles = (List<String>) claims.get("roles");
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        System.out.println("TokenProvider 의 getAuthentication 의 authorities = " + authorities);

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);

        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtinfo.getSKey())
                .parseClaimsJws(token)
                .getBody();
    }


}
