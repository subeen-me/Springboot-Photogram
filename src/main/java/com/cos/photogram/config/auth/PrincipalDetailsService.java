package com.cos.photogram.config.auth;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //1. 패스워드는 알아서 체킹한다.
    //2. UserDetails 타입으로 리턴이 잘 되면 자동으로 세션을 만든다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntitiy = userRepository.findByUsername(username);

        if (userEntitiy == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntitiy);
        }
    }
}
