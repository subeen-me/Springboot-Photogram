package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.SubscribeRepository;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;

    @Value("${file.path}") //application.yml의 path경로를 가져온다. yml에 내가 만든 키 값도 들고 올 수 있다.
    private String uploadFolder;

    @Transactional
    public User profileImageUpdate(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID(); //uuid
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename(); //실제 파일명(a.jpeg)이 들어간다
        System.out.println("이미지 파일이름 : "+imageFileName);

        Path ImageFilePath = Paths.get(uploadFolder+imageFileName); //실제 경로 저장. 경로+파일명

        //통신이나 I/O(하드디스크에 기록,읽을 때) -> 예외가 발생할 수 있다.
        try { //(파일패스, 실제 이미지 파일을 바이트화한 것, 마지막 옵션값 생략가능)
            Files.write(ImageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });

        userEntity.setProfileImageUrl(imageFileName);
        return userEntity;
    } // 더티체킹으로 업데이트된다.

    @Transactional(readOnly = true)
    public UserProfileDto profileView(int pageUserId, int principalId) {
        UserProfileDto dto = new UserProfileDto();

        //SELECT * FROM image WHERE userId = :userId
        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1); //1일 때 true
        dto.setSubscribeCount(subscribeCount);

        //좋아요 카운트 불러오기.
        userEntity.getImages().forEach(image -> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User userUpdate(int id, User user) {
        //1. 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()->{return new CustomValidationException("찾을 수 없는 id입니다.");});

        //2. 영속화된 오브젝트를 수정
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        //3. 수정된 유저엔티티를 리턴 -> 더티체킹(업데이트 완료)
        return userEntity;
    }


}
