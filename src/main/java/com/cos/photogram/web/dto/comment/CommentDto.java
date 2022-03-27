package com.cos.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//NotNull = Null값 체크
//NotEmpty = 빈값이거나 null 체크
//NotBlank = 빈값이거나 null 체크 그리고 빈 공백(스페이스)까지

@Data //story.js에서 addComment 안의 data를 가져오기 위해 dto 이용
public class CommentDto {
    @NotBlank // 빈값이거나 null, 빈 공백까지 체크
    private String content;
    @NotNull
    private Integer imageId;

    //toEntity가 필요없다.
}
