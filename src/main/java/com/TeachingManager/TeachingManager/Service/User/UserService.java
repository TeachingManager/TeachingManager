package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.Repository.User.UserRepository;
import com.TeachingManager.TeachingManager.config.jwt.JweInfo;
import com.TeachingManager.TeachingManager.config.jwt.JweUtil;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
    private final CustomUserDetailServiceImpl userDetailService;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JweUtil jwtUtil;
    private final JweInfo jweInfo;

    @Transactional
    // 요청이 들어온 이메일을 찾아보고, 있다면 토큰 생성해서 이메일을 전송
    public String sendEmailWithResetToken(String email, String IpAddress, String mod) throws Exception {
       try{
           userDetailService.loadUserByUsername(email);}
       catch(RuntimeException noUser) {
           return "존재하지 않는 회원입니다.";
        }
       
       // 토큰 생성 ( email, IpAddress, 만료시간 )
        String resetToken = tokenProvider.createResetToken(Duration.ofMinutes(5), email, IpAddress);
        System.out.println("resetToken = " + resetToken);

       // 해당 토큰을 담은 url 을 이메일에 담아 해당 유저에게 전송
        // mod 1 : 비번 찾기
        // mod 2 : 초기 이메일 인증
        // mod 3 : 비번 오입력 잠금 풀기
        if (Objects.equals(mod, "setNewPassword")) {
            // email 보내기
            return "사용자 정보 확인. 비밀번호 변경 메일이 보내졌습니다.";
        } else if (Objects.equals(mod, "initialAuthentication")) {
            // email 보내기
            return "사용자 정보 확인. 비밀번호 변경 메일이 보내졌습니다.";
        } else if (Objects.equals(mod, "unLockUser")) {
            // email 보내기
            return "사용자 정보 확인. 비밀번호 변경 메일이 보내졌습니다.";
        }

        return "잘못된 이메일 전송 요청 모드입니다.";
    }

    @Transactional
    public String changePassword( String token, String IpAddress, String newPassword ) throws Exception {
        // JWE 토큰 복호화
        String decryptedToken = JweUtil.decrypt(token, jweInfo.getSecretKey());

        // 토큰 claim 의 IP 값과 IPAddress 가 일치하는지 확인
        if (Objects.equals(IpAddress, tokenProvider.getUseIpInToken(decryptedToken))) {
            // 맞다면, 토큰 claim 의 email 로 유저 검색
            CustomUser user = null;
            try{
                String email = tokenProvider.getUseEmailInToken(decryptedToken);
                user = (CustomUser) userDetailService.loadUserByUsername(email);}
            catch(RuntimeException noUser) {
                return "존재하지 않는 회원입니다.";
            }
            // 비밀번호 변경
            user.setPassword(passwordEncoder.encode(newPassword));
            return "비밀번호 변경 완료!";
        }
        return "같은 아이피에서 요청해주십시오!";
    }

    @Transactional
    // 요청 받은 유저 잠금 해제 (비밀번호 여러번 오입력시 사용)
    public String unLockUser(String token, String IpAddress) throws Exception {
        // JWE 토큰 복호화
        String decryptedToken = JweUtil.decrypt(token, jweInfo.getSecretKey());

        // 토큰 claim 의 IP 값과 IPAddress 가 일치하는지 확인
        if (Objects.equals(IpAddress, tokenProvider.getUseIpInToken(decryptedToken))) {
            // 맞다면, 토큰 claim 의 email 로 유저 검색
            CustomUser user = null;
            try{
                String email = tokenProvider.getUseEmailInToken(decryptedToken);
                user = (CustomUser) userDetailService.loadUserByUsername(email);}
            catch(RuntimeException noUser) {
                return "존재하지 않는 회원입니다.";
            }
            // 잠금 해제 && 비밀번호 틀린 횟수 초기화
            user.setAccountNonLocked(true);
            user.setFailedCount((byte) 0);
            
            return "계정 잠금 해제 완료!";
        }

        return "같은 아이피에서 요청해주십시오!";
    }

    @Transactional
    // 요청 받은 유저 잠금 해제 ( 신규 회원가입 시 사용)
    public String enableUser(String token, String IpAddress) throws Exception {
        // JWE 토큰 복호화
        String decryptedToken = JweUtil.decrypt(token, jweInfo.getSecretKey());

        // 토큰 claim 의 IP 값과 IPAddress 가 일치하는지 확인
        if (Objects.equals(IpAddress, tokenProvider.getUseIpInToken(decryptedToken))) {
            // 맞다면, 토큰 claim 의 email 로 유저 검색
            CustomUser user = null;
            try{
                String email = tokenProvider.getUseEmailInToken(decryptedToken);
                user = (CustomUser) userDetailService.loadUserByUsername(email);}
            catch(RuntimeException noUser) {
                return "존재하지 않는 회원입니다.";
            }
            // 계정의 enable 을 true 로 설정.
            user.setEnabled(true);
//             save 메소드 안해도 되는지 체크 (더티체킹)

            return "계정 잠금 해제 완료!";
        }

        return "같은 아이피에서 요청해주십시오!";
    }
}
