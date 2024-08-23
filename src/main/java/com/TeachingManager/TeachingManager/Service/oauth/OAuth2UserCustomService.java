package com.TeachingManager.TeachingManager.Service.oauth;

import com.TeachingManager.TeachingManager.DTO.Teacher.SocialTeacherInfo;
import com.TeachingManager.TeachingManager.Repository.User.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final TeacherRepository teacherRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("loadUser 안의 userRequest = " + userRequest);
        OAuth2User user = super.loadUser(userRequest);

//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        String accessToken = userRequest.getAccessToken().getTokenValue();
//
//        System.out.println("accessToken = " + accessToken);

//        SocialTeacherInfo socialTeacherInfo = saveOrUpdate(user, "google");
        return user;
    }

    private SocialTeacherInfo saveOrUpdate(OAuth2User oAuth2User, String provider) {
        return null;
    }
}
