package com.TeachingManager.TeachingManager.config;//package com.TeachingManager.TeachingManager.config;

import com.TeachingManager.TeachingManager.EventHandler.InstitutonAuthenticationFailureHandler;
import com.TeachingManager.TeachingManager.Service.User.CustomUserDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Institute.InstituteDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.oauth.OAuth2UserCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig{

    private final InstituteDetailServiceImpl instituteDetailService;
    private final CustomUserDetailServiceImpl userDetailService;
    private final TeacherDetailServiceImpl teacherDetailService;
    private final OAuth2UserCustomService oAuth2Service;

    @Autowired
    private InstitutonAuthenticationFailureHandler institutonAuthenticationFailureHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**");
    }

//     HTTP 의 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/login/institute", "/signup/institute", "/institute",
                                "/login/teacher", "/signup/teacher","/signup/social/teacher", "/teacher","/oauth2/authorization/google"
                        ).permitAll() // 로그인, 회원가입은 인증 x
                        .anyRequest().authenticated() // 다른 모든 요청은 인증 필요.
                )
                .formLogin(form -> form.loginPage("/login/institute")
                        .loginProcessingUrl("/login/institute/check")
                        .defaultSuccessUrl("/home", true)
                        .failureHandler(institutonAuthenticationFailureHandler)
                        .usernameParameter("email")  // 이메일을 username으로 사용
                        .passwordParameter("password")
                )
                .formLogin(form-> form.loginPage("/login/teacher")
                        .loginProcessingUrl("/login/teacher/check")
                        .defaultSuccessUrl("/home", true)
                        .failureHandler(institutonAuthenticationFailureHandler)
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                                .oauth2Login(oauth2 -> oauth2 // OAuth2를 통한 로그인 사용
                        .defaultSuccessUrl("/home", true) // 로그인 성공시 이동할 URL
                        .userInfoEndpoint(userInfo -> userInfo // 사용자가 로그인에 성공하였을 경우,
                                .userService(oAuth2Service) // 해당 서비스 로직을 타도록 설정
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login/institute")
                        .invalidateHttpSession(true)
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
        return auth.build();
    }
}
