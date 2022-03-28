package com.cos.photogram.handler.aop;

import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //RestController, Service 모든 것들이 Component 상속해서 만들어져있음.
@Aspect //Aop 처리 가능 핸들러
public class ValidationAdvice {

    //ApiController에 있는 메서드가 실행될 때 마다
    //그 메서드에 있는 모든 정보를 proceedingJoinPoint가 갖고 여기로 와서 먼저 실행된다.
    @Around("execution(* com.cos.photogram.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) { //BindingResult가 사용될 때 마다 여기로 호출
            if (arg instanceof BeanPropertyBindingResult) {
                System.out.println("유효성 검사를 하는 메서드입니다.");
                BindingResult bindingResult = (BindingResult) arg; //다운캐스팅

                if (bindingResult.hasErrors()) { //error가 있으면 CustomValidationApiException로 던진다
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패", errorMap); //error가 있으면 강제로 runtimeexeption을 발동시킨다. handler로 연결
                }

            }
        }

        //proceedingJoinPoint : 메서드 내 모든 곳에 접근할 수 있는 변수
        //Controller 안에 있는 메서드보다 먼저 실행

        return proceedingJoinPoint.proceed(); // 메서드가 이제 실행됨. 호출된 곳으로 돌아간다.
    }

    @Around("execution(* com.cos.photogram.web.*.Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        //  System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap); //error가 있으면 강제로 runtimeexeption을 발동시킨다. handler로 연결
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }


}
