package com.cos.photogram.web.dto.auth;

import com.cos.photogram.domain.user.User;
import lombok.Data;

@Data //getter, setter, toString
public class SignupDto { //통신할 때 필요한 데이터를 담아두는 오브젝트
    private String username;
    private String password;
    private String email;
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
