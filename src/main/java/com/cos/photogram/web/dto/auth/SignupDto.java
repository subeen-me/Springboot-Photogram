package com.cos.photogram.web.dto.auth;

import com.cos.photogram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data //getter, setter, toString
public class SignupDto { //통신할 때 필요한 데이터를 담아두는 오브젝트

    @NotBlank @Size(min = 2, max = 20)//Valid. 길이제한 20자
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
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
