package com.cos.photogram.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
    private int id; //api호출 시 toUserId, 누구를 구독할지, '누구를'에 해당하는 Id
    private String username;
    private String profileImageUrl;
    private Integer subscribeState; //구독상태 확인
    private Integer equalUserState; //로그인한 사용자와 동일인인지 아닌지 확인

}
