package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.subscribe.Subscribe;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.service.SubscribeService;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.subscribe.SubscribeDto;
import com.cos.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{principalId}/profileImageUrl") //MultipartFile 변수이름을 profile.jsp의 name값과 일치시킨다
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User userEntity = userService.profileImageUpdate(principalId, profileImageFile);
        principalDetails.setUser(userEntity); // 세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe") //{pageUserId}는 내가 현재 이동한 그 페이지 주인이 구독하고 있는 모든 정보
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult, //꼭 @Valid 가 적혀있는 다음 파라미터에 적어야 한다.
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

            User userEntity = userService.userUpdate(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //세션정보 새로 넣고 저장(변경)
            return new CMRespDto<>(1, "회원수정완료", userEntity); //응답시에 userEntity의 모든 getter함수가 호출되고 json으로 파싱하여 응답한다
        }

}
