package com.cos.photogram.domain.user;

import com.cos.photogram.domain.Image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    //mappedBy : 연관관계의 주인이 아니다. 테이블에 컬럼을 만들지 않는다.
    //image클래스의 변수 user를 넣어준다.
    //User를 Select할 때 해당 User의 id로 등록된 image들을 다 가져온다.
    //LAZY = User를 select할 때 해당 User의 id로 등록된 image들을 가져오지 않는다. 대신 getImages()함수의 image들이 호출될 때 가져온다.
    //EAGER = select할 때 관련된 image들을 전부 join해서 가져온다.
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"user"}) // Image 내부에 있는 user를 제외하고 파싱한다. 응답할 때 무한참조 방지
    private List<Image> images; //양방향 매핑

    private LocalDateTime createDate;

    @PrePersist //db에 insert되기 직전에 실행하게 만드는 어노테이션
    public void createDate() { //LocalDateTime 자동으로 입력
        this.createDate = LocalDateTime.now();
    }

    @Override //aop시 무한참조 막기위해 Images 제외한 toString 생성
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
