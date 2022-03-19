package com.cos.photogram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

//공통 응답 dto

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {

    private int code; // 1(성공), -1(실패)
    private String message;
    private T data; //아무 타입이나 리턴가능하도록 제네릭 타입으로 설정
}
