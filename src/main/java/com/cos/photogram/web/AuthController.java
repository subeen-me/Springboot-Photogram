package com.cos.photogram.web;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.service.AuthService;
import com.cos.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    //BindigResult는 SignupDto에서 유효성 검사한 것과 관련, dto에서 오류가 발생하면 bindingresult의 getFieldErrors에 다 모아진다.
    //원래는 String 타입 리턴+Controller이기 때문에 파일을 리턴하지만, 리턴타입 앞에 @ResponseBody라고 적어주면 Controller긴 한데 data를 리턴한다.
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { //key=value (x-www-form-urlencoded)

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap); //error가 있으면 강제로 runtimeexeption을 발동시킨다. handler로 연결
        } else {
            //User에 signupDto를 넣을 예정
            User user = signupDto.toEntity();
            User userEntity = authService.signup(user);
            System.out.println(userEntity);
            return "/auth/signin";
        }

    }
}
