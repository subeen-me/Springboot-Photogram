package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User userUpdate(int id, User user) {
        //1. 영속화
        User userEntity = userRepository.findById(id).get();
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
