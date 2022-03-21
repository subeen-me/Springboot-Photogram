package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
