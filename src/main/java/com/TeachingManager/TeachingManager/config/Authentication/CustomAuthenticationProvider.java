package com.TeachingManager.TeachingManager.config.Authentication;

import com.TeachingManager.TeachingManager.Service.User.CustomUserDetailServiceImpl;
import com.TeachingManager.TeachingManager.config.exceptions.UserDisabledException;
import com.TeachingManager.TeachingManager.config.exceptions.UserDoesNotExistException;
import com.TeachingManager.TeachingManager.config.exceptions.UserLockedException;
import com.TeachingManager.TeachingManager.config.exceptions.WrongPasswordException;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailServiceImpl userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // 사용자 정보 로드
        CustomUser user = userDetailsService.loadCustomUserByUsername(username);

        System.out.println("정보 로드 완료");

        // 고정된 시간 대기
        try {
            Thread.sleep(100); // 예: 100ms 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 사용자 유무 검사
        if (user == null) {
            throw new UserDoesNotExistException("사용자를 찾을 수 없습니다.");
        }else{
            System.out.println("사용자 찾음!");
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
            System.out.println("비밀번호가 틀렸다.");
            throw new WrongPasswordException("잘못된 자격 증명입니다.", user.getPk());
        }

        if (!user.isAccountNonLocked()){
            throw new UserLockedException("잠긴 사용자 입니다."); //  추후에 따로 사용자 지정 exception 만들어서 GlobalExceptionHandler 에서 받게하기
        }

        if (!user.isEnabled()){
            throw new UserDisabledException("사용자가 비활성화되었습니다."); //  추후에 따로 사용자 지정 exception 만들어서 GlobalExceptionHandler 에서 받게하기
        }

        // 인증 성공 시 사용자 정보 반환
        System.out.println("인증성공!");
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
