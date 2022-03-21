package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.service.SubscribeService;
import com.cos.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}") //구독하기 - 현재 로그인한 사람이 구독하는 것.
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable int toUserId) {
        subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1,"구독 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @PathVariable int toUserId) {
        subscribeService.unSubscribe(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new CMRespDto<>(1,"구독 취소 성공", null), HttpStatus.OK);
    }
}
