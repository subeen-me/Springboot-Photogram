package com.cos.photogram.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//JPA - Java Persistence Api (자바로 데이터를 영구적으로 저장(DB)할 수 있는 Api를 제공)
@Builder //dto에서 빌더패턴을 이용하기 위해 사용. toEntity 메서드
@AllArgsConstructor //전체 생성자
@NoArgsConstructor //빈 생성자
@Data //getter, setter 생성
@Entity //db에 테이블을 생성
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략을 DB를 따라간다
    private int id;

    @Column(length = 20, unique = true) //회원가입 시 중복 아이디 방지
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    private String website; //웹사이트
    private String bio; //자기소개

    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; //사진
    private String role; //권한

    private LocalDateTime createDate;

    @PrePersist //db에 insert되기 직전에 실행하게 만드는 어노테이션
    public void createDate() { //LocalDateTime 자동으로 입력
        this.createDate = LocalDateTime.now();
    }

}
