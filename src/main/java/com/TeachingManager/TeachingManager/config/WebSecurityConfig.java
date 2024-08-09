package com.TeachingManager.TeachingManager.config;//package com.TeachingManager.TeachingManager.config;

import com.TeachingManager.TeachingManager.EventHandler.InstitutonAuthenticationFailureHandler;
import com.TeachingManager.TeachingManager.Service.User.CustomUserDetailServiceImpl;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.Service.oauth.OAuth2UserCustomService;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig{

    private final CustomUserDetailServiceImpl userDetailService;
    private final OAuth2UserCustomService oAuth2Service;
    private final TokenProvider tokenProvider;

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
                                .requestMatchers("/api/login","login","/api/accessToken", "/signup/institute", "/institute",
                                        "/signup/teacher","/signup/social/teacher", "/oauth2/authorization/google"
                        ).permitAll()//로그인, 회원가입은 인증 xq
                        .requestMatchers("/api/fee", "/api/teacher", "/teacher").hasRole("PRESIDENT")// 수강료, 선생님 api 등은 학원장만
                        .anyRequest().authenticated() // 다른 모든 요청은 인증 필요.

                )
                // 세션 로그인 방식을 해제
//                .formLogin(form -> form.loginPage("/login/institute")
//                        .loginProcessingUrl("/login/institute/check")
//                        .defaultSuccessUrl("/home", true)
//                        .failureHandler(institutonAuthenticationFailureHandler)
//                        .usernameParameter("email")  // 이메일을 username으로 사용
//                        .passwordParameter("password")
//                )
                // oAUTH 2.0 로그인
//                .oauth2Login(oauth2 -> oauth2 // OAuth2를 통한 로그인 사용
//                        .defaultSuccessUrl("/home", true) // 로그인 성공시 이동할 URL
//                        .userInfoEndpoint(userInfo -> userInfo // 사용자가 로그인에 성공하였을 경우,
//                                .userService(oAuth2Service) // 해당 서비스 로직을 타도록 설정
//                        )
//                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login/institute")
                        .invalidateHttpSession(true)
                )
                .csrf(csrf -> csrf.disable())
                // jwt 토큰 필터
                .addFilterBefore(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
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

//    @Bean
//    public OAuth2SuccessHandler oAuth2SuccessHandler(){
//
//    }
}
