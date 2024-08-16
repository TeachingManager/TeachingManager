package com.TeachingManager.TeachingManager.config.jwt;

import com.TeachingManager.TeachingManager.Repository.User.RefreshTokenRepository;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.RefreshToken;
import com.TeachingManager.TeachingManager.domain.Teacher;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtInfo jwtinfo;
    private final JweInfo jweInfo;
    private final JweUtil jweUtil;
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

// 이미 refreshToken 이 존재하는지 체크하고 있다면 업데이트, 아니라면 저장
        Optional<RefreshToken> refreshToken = refreshTokenRepo.findByUserId(user.getPk());
        RefreshToken newRefreshToken = new RefreshToken(user.getPk(), token, expired_time);

        if(refreshToken.isPresent()) {
            refreshToken.get().update(token);
        }
        else {
            refreshTokenRepo.save(newRefreshToken);
        }

        return token;
    }

    // 비밀번호 찾기, 초기 회원가입시 사용할 본인인증 토큰 발급
    public String createResetToken(Duration expiredAt, String userEmail, String IpAddress) throws Exception {
        Date now = new Date();
        Date expired_time = new Date(now.getTime() + expiredAt.toMillis());

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setIssuedAt(now)
                .setExpiration(expired_time)
                .setSubject(userEmail)
                .claim("email", userEmail)
                .claim("IP", IpAddress)
                .signWith(SignatureAlgorithm.HS512, jwtinfo.getSKey())
                .compact();
        System.out.println("jwt = " + jwt);
        return JweUtil.encrypt(jwt, jweInfo.getSecretKey());
    }

    public String createJoinToken(Duration expiredAt, String teacherEmail, String instEmail) throws Exception {
        Date now = new Date();
        Date expired_time = new Date(now.getTime() + expiredAt.toMillis());

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setIssuedAt(now)
                .setExpiration(expired_time)
                .setSubject(teacherEmail)
                .claim("email", teacherEmail)
                .claim("inst_email", instEmail)
                .signWith(SignatureAlgorithm.HS512, jwtinfo.getSKey())
                .compact();
        System.out.println("jwt = " + jwt);
        return JweUtil.encrypt(jwt, jweInfo.getSecretKey());
    }


// private 을 이용하여 접근제한?
    private String createToken(Date expiredDate, CustomUser user){
        Date now = new Date();
        String token = null;
        if (user instanceof Institute){
            token = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS512")
                    .setIssuedAt(now)
                    .setExpiration(expiredDate)
                    .setSubject(user.getEmail())
                    .claim("id", user.getPk().toString())
                    .claim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .signWith(SignatureAlgorithm.HS512, jwtinfo.getSKey())
                    .compact();
        }
        else if(user instanceof Teacher){
            token = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS512")
                    .setIssuedAt(now)
                    .setExpiration(expiredDate)
                    .setSubject(user.getEmail())
                    .claim("id", user.getPk().toString())
                    .claim("inst_id", ((Teacher) user).getInstitutePk())
                    .claim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .signWith(SignatureAlgorithm.HS512, jwtinfo.getSKey())
                    .compact();
        }

        return token;
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
        System.out.println("roles = " + roles);


        /////////////////// 여기에서 Teacher 이랑 Institute  나누어서 구분하기.
        //////////////////////////////////
        if(roles.contains("ROLE_PRESIDENT")){
            String id = claims.get("id", String.class);
            CustomUser customUser = new CustomUser((UUID.fromString(id)), claims.getSubject(), "", roles);
            return new UsernamePasswordAuthenticationToken(customUser, token, authorities);
        }
        else if(roles.contains("ROLE_TEACHER")){
            String id = claims.get("id", String.class);
            String inst_id = claims.get("inst_id", String.class);
            Teacher teacher = new Teacher(claims.getSubject(), "", (UUID.fromString(id)),(UUID.fromString(inst_id)));
            return new UsernamePasswordAuthenticationToken(teacher, token, authorities);
        }
        return null;
    }

    public String getUseEmailInToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    public String getInstUserEmailInToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("inst_email", String.class);
    }

    public String getUseIpInToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("IP", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtinfo.getSKey())
                .parseClaimsJws(token)
                .getBody();
    }

}
