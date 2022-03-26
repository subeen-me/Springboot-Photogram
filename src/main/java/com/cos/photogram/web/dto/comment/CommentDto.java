package com.cos.photogram.web.dto.comment;

import lombok.Data;

@Data //story.js에서 addComment 안의 data를 가져오기 위해 dto 이용
public class CommentDto {
    private String content;
    private int imageId;

    //toEntity가 필요없다.
}
