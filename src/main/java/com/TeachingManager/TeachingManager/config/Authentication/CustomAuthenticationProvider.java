package com.TeachingManager.TeachingManager.config.Authentication;

import com.TeachingManager.TeachingManager.Service.User.CustomUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailServiceImpl userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // 사용자 정보 로드
        UserDetails user = userDetailsService.loadUserByUsername(username);

        // 고정된 시간 대기
        try {
            Thread.sleep(100); // 예: 100ms 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 사용자 유무 검사
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 검증
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());

        // 고정된 시간 대기
        try {
            Thread.sleep(100); // 예: 100ms 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!passwordMatches) {
            throw new BadCredentialsException("잘못된 자격 증명입니다.");
        }

        // 인증 성공 시 사용자 정보 반환
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
