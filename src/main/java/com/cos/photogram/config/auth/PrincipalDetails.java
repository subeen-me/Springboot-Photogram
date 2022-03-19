package com.cos.photogram.config.auth;

import com.cos.photogram.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //권한 : 한 개가 아닐 수 있다. 어떤 사람은 3개 이상의 권한을 가질 수도 있다. 그래서 기본 collection 타입으로 되어있다
    @Override //권한을 가져오는 메서드
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collector = new ArrayList<>(); //arraylist 부모가 colletion
        collector.add(() -> { //함수를 넘기기 위해 람다식 사용
            return user.getRole();
        });
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override //계정 만료 확인. 만료되지 않았을 때 true
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //계정 잠금 확인
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //계정 비밀번호 만료 확인
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override //계정 활성화 여부 확인
    public boolean isEnabled() {
        return true;
    }
}
