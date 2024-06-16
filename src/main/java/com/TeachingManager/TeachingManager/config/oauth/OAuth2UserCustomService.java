package com.TeachingManager.TeachingManager.config.oauth;

import com.TeachingManager.TeachingManager.Repository.Teacher.TeacherRepository;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    private Teacher saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Optional<Teacher> teacher = teacherRepo.searchByEmail(email);

        // 이미 있는 선생님일 경우 업데이트하지 않고 그대로 전달
        if (teacher.isPresent()) {
            return teacher.get();
        }
        // 기존에 가입하지 않았던 user 일 경우
        else {
            Teacher newTeacher = new Teacher();
            newTeacher.setEmail(email);
            newTeacher.setTeacher_name(name);
            return newTeacher;
        }
    }
}
