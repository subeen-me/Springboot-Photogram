package com.cos.photogram.web;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.Image.Image;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.service.ImageService;
import com.cos.photogram.web.dto.Image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story() {
        return "image/story";
    }

    //만약 api 구현한다면 이유 : 요청을 브라우저에서 하는 게 아니라 안드로이드나 ios같은 데서 요청을 하게 될 때. data를 줘야하기 때문
    //이 메소드는 페이지를 리턴
    @GetMapping({"/image/popular"})
    public String popular(Model model) { //data를 담아가기 위해 model 사용
        //api는 데이터를 리턴하는 서버. 이건 모델에 담고 데이터를 들고가기만 하면 되기 때문에 ImageController에서 한다
        List<Image> images = imageService.popularFeed();
        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping({"/image/upload"})
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //공통처리 부분이 아니므로 남겨둔다
        if(imageUploadDto.getFile().isEmpty()) { //page를 응답할 것이기 때문에 validationexception으로 한다
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }

        //서비스 호출
        imageService.upload(imageUploadDto, principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId(); //현재 로그인한 사용자의 id를 가져와서 개인 피드로 이동
    }

}
