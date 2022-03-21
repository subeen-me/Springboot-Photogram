package com.cos.photogram.service;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.Image.Image;
import com.cos.photogram.domain.Image.ImageRepository;
import com.cos.photogram.web.dto.Image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}") //application.yml의 path경로를 가져온다. yml에 내가 만든 키 값도 들고 올 수 있다.
    private String uploadFolder;

    @Transactional
    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID(); //uuid
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); //실제 파일명(a.jpeg)이 들어간다
        System.out.println("이미지 파일이름 : "+imageFileName);

        Path ImageFilePath = Paths.get(uploadFolder+imageFileName); //실제 경로 저장. 경로+파일명

        //통신이나 I/O(하드디스크에 기록,읽을 때) -> 예외가 발생할 수 있다.
        try { //(파일패스, 실제 이미지 파일을 바이트화한 것, 마지막 옵션값 생략가능)
            Files.write(ImageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); //이미지 객체 생성
        imageRepository.save(image); //save하면 imageEntity 저장됨

        //System.out.println(imageEntity);
    }
}
