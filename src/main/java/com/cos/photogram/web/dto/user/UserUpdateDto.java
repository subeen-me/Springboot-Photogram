package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name; //필수
    @NotBlank
    private String password; //필수
    private String website;
    private String bio;
    private String phone;
    private String gender;

    //조금 위험함. 코드 수정이 필요할 예정
    public User toEntity() {
        return User.builder()
                .name(name) //이름 기재하지 않았으면 문제. Validation 체크
                .password(password) //Validation 체크
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
