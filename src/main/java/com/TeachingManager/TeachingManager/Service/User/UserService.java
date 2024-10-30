package com.TeachingManager.TeachingManager.Service.User;

import com.TeachingManager.TeachingManager.DTO.User.InviteRequest;
import com.TeachingManager.TeachingManager.Repository.User.Institute.InstituteRepository;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.config.exceptions.UserDoesNotExistException;
import com.TeachingManager.TeachingManager.config.jwt.JweInfo;
import com.TeachingManager.TeachingManager.config.jwt.JweUtil;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Institute;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final CustomUserDetailServiceImpl userDetailService;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JweUtil jwtUtil;
    private final JweInfo jweInfo;
    private final TeacherRepository teacherRepo;
    private final InstituteRepository instituteRepo;
    private final TemplateEngine templateEngine;


    // 비밀번호 틀릴시 1 증가.
    @Transactional
    public String increaseFailCount(UUID pk) {
        // 먼저 유저를 찾는다.
        CustomUser user = userDetailService.loadCustomUserByPk(pk);
        user.setFailedCount((byte) (user.getFailedCount() + 1));
        if(user.getFailedCount() >= 5) {
            user.setAccountNonLocked(false);
            return "비번5회 틀림! : 계정이 잠겼습니다!";
        }
        return "비밀번호 오류! 틀린 횟수 :  " + user.getFailedCount() + "/5";
    }


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
        // 이렇게 안보내도, HTML 으로 들어가면 encode 되더라.....
        String encodedResetToken = URLEncoder.encode(resetToken, StandardCharsets.UTF_8);
        System.out.println("encodedResetToken = " + encodedResetToken);

       // 해당 토큰을 담은 url 을 이메일에 담아 해당 유저에게 전송
        // mod unLockUser : 비번 찾기
        // mod initialAuthentication : 초기 이메일 인증
        // mod setNewPassword : 비번 오입력 잠금 풀기
        if (Objects.equals(mod, "setNewPassword")) {
            // email 보내기
            sendEmail(email,"TeachingManager - 비밀번호 변경용 이메일입니다.","EmailForm/FixPasswordEmail", resetToken);
            return "사용자 정보 확인. 비밀번호 변경 메일이 보내졌습니다.";
        } else if (Objects.equals(mod, "initialAuthentication")) {
            // email 보내기
            sendEmail(email,"TeachingManager - 본인 확인용 이메일입니다.","EmailForm/InitialAuthenticationEmail", resetToken);
            return "사용자 정보 확인. 초기 인증용 메일이 보내졌습니다.";
        } else if (Objects.equals(mod, "unLockUser")) {
            // email 보내기
            sendEmail(email,"TeachingManager - 잠금 해제용 이메일입니다.","EmailForm/UnlockAccountEmail", resetToken);
            return "사용자 정보 확인. 잠금해제용 메일이 보내졌습니다.";
        }

        return "잘못된 이메일 전송 요청 모드입니다.";
    }

    @Transactional
    public String changePassword( String token, String IpAddress, String newPassword ) throws Exception {

//        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
//        String decryptedToken = JweUtil.decrypt(decodedToken, jweInfo.getSecretKey());

        String decryptedToken = JweUtil.decrypt(token, jweInfo.getSecretKey());

        System.out.println("newPassword = " + newPassword);

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


    /////////////////////////////////////////////////////////////
    /////////////       잠금 해제 서비스           ////////////////
    /////////////////////////////////////////////////////////////

    @Transactional
    // 요청 받은 유저 잠금 해제 (비밀번호 여러번 오입력시 사용)
    public String unLockUser(String token, String IpAddress) throws Exception {


//        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
//        String decryptedToken = JweUtil.decrypt(decodedToken, jweInfo.getSecretKey());
//
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

    /////////////////////////////////////////////////////////////
    /////////////        신규 회원 인증            ////////////////
    /////////////////////////////////////////////////////////////
    @Transactional
    // 요청 받은 유저 잠금 해제 ( 신규 회원가입 시 사용)
    public String enableUser(String token, String IpAddress) throws Exception {

        // JWE 토큰 복호화
        String decryptedToken = JweUtil.decrypt(token, jweInfo.getSecretKey());
        System.out.println("decryptedToken = " + decryptedToken);

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

            return "계정 잠금 해제 완료!";
        }

        return "같은 아이피에서 요청해주십시오!";
    }

    /////////////////////////////////////////////////////////////
    /////////////       강사 초대 서비스           ////////////////
    /////////////////////////////////////////////////////////////

    @Transactional
    public String sendEmailWithJoinToken(InviteRequest request, CustomUser user) throws Exception {

        if (!Objects.equals(request.getInstitute_email(), user.getEmail())) {
            return "강사에게 잘못된 학원 정보로 초대를 보냈음!";
        }

        Teacher teacher = teacherRepo.findByEmail(request.getTeacher_email()).orElseThrow(() -> new UserDoesNotExistException("없는 강사가 참가하려함."));
        if(teacher.getInstitutePk() == null) {
            Institute institute = instituteRepo.findByEmail(request.getInstitute_email()).orElseThrow(()->new RuntimeException("없는 학원으로 부터의 초대를 보내려함."));

            // 토큰 생성 ( email, inst_email, 만료시간 )
            String joinToken = tokenProvider.createJoinToken(Duration.ofMinutes(10), request.getTeacher_email(), request.getInstitute_email());
            String encodedJoinToken = URLEncoder.encode(joinToken, StandardCharsets.UTF_8);
            System.out.println("강사 초대의 encodedJoinToken = " + encodedJoinToken);

            // email 보내기
            sendEmail(request.getTeacher_email(),"TeachingManager  - " + institute.getInstitute_name() + "으로부터의 초대가 도착했습니다.",
                    "/EmailForm/InviteAccountEmail", joinToken);
            return "강사에게 초대 이메일을 전송했습니다..";
        }
        else{
            return "이미 학원 등록된 강사입니다.";
        }

    }

    @Transactional
    public String joinTeacherToInstitute(String token) throws Exception {
//        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
//        String decryptedToken = JweUtil.decrypt(decodedToken, jweInfo.getSecretKey());

        String decryptedToken = JweUtil.decrypt(token, jweInfo.getSecretKey());

        System.out.println("/////////////////////////////");
        System.out.println("decryptedToken = " + decryptedToken);
        String email = tokenProvider.getUseEmailInToken(decryptedToken);
        String inst_email = tokenProvider.getInstUserEmailInToken(decryptedToken);

        Institute institute = instituteRepo.findByEmail(inst_email).orElseThrow(() -> new UserDoesNotExistException("없는 학원에 참가하려함."));
        Teacher teacher = teacherRepo.findByEmail(email).orElseThrow(() -> new UserDoesNotExistException("없는 강사가 참가하려함."));

        if (teacher.getInstitutePk() == null) {
            teacher.setInstitute(institute);
            return "가입완료";
        }
        else{
            return "이미 가입된 학원이 있습니다!";
        }
    }


    /////////////////////////////////////////////////////////////
    /////////////       이메일 발송 서비스   /      ////////////////
    /////////////////////////////////////////////////////////////

    public void sendEmail(String to, String subject, String templateLocation, String token) {
        String host = "email-smtp.ap-northeast-2.amazonaws.com";
        final String username = "AKIA5FTZCSHK523ULN56";
        final String password = "BAYxpeOZmEwyflRaXcQkRu6CwVqbJTYAV8QPB7HQmLvC";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS 사용

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Context context = new Context();
            context.setVariable("token", token);
            String htmlBody = templateEngine.process(templateLocation, context);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("teachingmanager@outlook.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // 이메일 제목을 인코딩하여 설정
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));

            // 이메일 내용을 설정하고 인코딩
            message.setContent(htmlBody, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Email was successfully sent!");

        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
