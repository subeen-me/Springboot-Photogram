package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.service.ImageService;
import com.cos.photogram.web.dto.Image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story() {
        return "image/story";
    }

    @GetMapping({"/image/popular"})
    public String popular() {
        return "image/popular";
    }

    @GetMapping({"/image/upload"})
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(imageUploadDto.getFile().isEmpty()) { //page를 응답할 것이기 때문에 validationexception으로 한다
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }

        //서비스 호출
        imageService.upload(imageUploadDto, principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId(); //현재 로그인한 사용자의 id를 가져와서 개인 피드로 이동
    }

}
