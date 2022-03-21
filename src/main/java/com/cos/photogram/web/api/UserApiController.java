package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult, //꼭 @Valid 가 적혀있는 다음 파라미터에 적어야 한다.
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패", errorMap); //error가 있으면 강제로 runtimeexeption을 발동시킨다. handler로 연결
        } else {
            User userEntity = userService.userUpdate(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //세션정보 새로 넣고 저장(변경)
            return new CMRespDto<>(1, "회원수정완료", userEntity); //응답시에 userEntity의 모든 getter함수가 호출되고 json으로 파싱하여 응답한다
        }

    }
}
