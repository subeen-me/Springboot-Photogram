package com.cos.photogram.handler;

import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //모든 Exception을 낚아챈다
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //RuntimeException이 발동한 모든 익셉션은 여기로 온다
    public CMRespDto<?> validationException(CustomValidationException e) {
        return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
    }
}
