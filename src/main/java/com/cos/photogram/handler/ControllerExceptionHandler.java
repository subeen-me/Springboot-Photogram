package com.cos.photogram.handler;

import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.util.Script;
import com.cos.photogram.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //모든 Exception을 낚아챈다
public class ControllerExceptionHandler {

    //RuntimeException이 발동한 모든 익셉션은 여기로 온다

    @ExceptionHandler(CustomValidationException.class) //RuntimeException이 발동한 모든 익셉션은 여기로 온다
    public String validationException(CustomValidationException e) {
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할 때는 Script가 좋다. 브라우저가 만든 것
        // 2. Ajax 통신을 할 때는 CMRespDto. 응답을 개발자가 만든 것
        // 3. Android 통신 - CMRespDto
        if(e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString()); //사용자에게 메세지 보낸다. 자바스크립트를 리턴
        }
    }

    @ExceptionHandler(CustomException.class) //RuntimeException이 발동한 모든 익셉션은 여기로 온다
    public String Exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>( new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()),HttpStatus.BAD_REQUEST); //데이터를 리턴
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>( new CMRespDto<>(-1, e.getMessage(), null),HttpStatus.BAD_REQUEST); //데이터를 리턴
    }
}
