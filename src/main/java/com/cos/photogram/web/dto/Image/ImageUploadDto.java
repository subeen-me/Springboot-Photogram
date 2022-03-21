package com.cos.photogram.web.dto.Image;

import com.cos.photogram.domain.Image.Image;
import com.cos.photogram.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    private MultipartFile file; //multifile validation에는 notblank가 지원되지 않는다
    private String caption;

    public Image toEntity(String postImageUrl, User user) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl) //정확한 경로
                .user(user) //user.get.. 필요 없이 user 오브젝트를 그대로 넣는다.
                .build();
    }

}
