package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //Service 어노테이션이 등록된 객체 : IoC 등록, 트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional //Write(Insert, Update, Delete) 할 때 @Transactional 을 쓴다
    public User signup(User user) {
        //회원가입 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); //해쉬 암호화
        user.setPassword(encPassword);
        user.setRole("ROLE_USER"); //관리자는 ROLE_ADMIN
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
