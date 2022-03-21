package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserProfileDto {
    private boolean pageOwnerState;
    //profile.jsp에서 user.images.size()로 들고올 수 있지만
    //view 페이지에서 연산하는 걸 막기 위해 필드 생성
    private int imageCount;
    private User user;
}
