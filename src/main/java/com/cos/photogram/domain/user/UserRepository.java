package com.cos.photogram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 JpaRepository를 상속하면 IoC 등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Integer> {

    //JPA naming query 사용. Spring data JPA QueryCreation 공식문서 활용
    User findByUsername(String username);
}
