package com.cos.photogram.web;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.service.AuthService;
import com.cos.photogram.web.dto.auth.SignupDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller //IoC등록, 파일을 리턴하는 컨트롤러
@Slf4j
@RequiredArgsConstructor //final 필드의 생성자 생성. DI할 때 사용
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "/auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "/auth/signup";
    }

    //회원가입 버튼 -> /auth/signup -> /auth/signin
    //회원가입 진행 메서드. 회원가입이 끝나면 로그인 페이지로 리턴
    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto) { //key=value (x-www-form-urlencoded)
        //User에 signupDto를 넣을 예정
        User user = signupDto.toEntity();
        User userEntity = authService.signup(user);
        System.out.println(userEntity);
        return "/auth/signin";
    }
}
