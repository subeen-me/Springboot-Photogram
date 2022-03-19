package com.cos.photogram.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

    //객체를 구분할 때 사용. 자바 런타임 시 이용. JVM에게 유용
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
