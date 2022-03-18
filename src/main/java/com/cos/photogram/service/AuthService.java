package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service //Service 어노테이션이 등록된 객체 : IoC 등록, 트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User signup(User user) {
        //회원가입 진행
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
