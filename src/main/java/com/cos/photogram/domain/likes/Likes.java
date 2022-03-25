package com.cos.photogram.domain.likes;


import com.cos.photogram.domain.Image.Image;
import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder //dto에서 빌더패턴을 이용하기 위해 사용. toEntity 메서드
@AllArgsConstructor //전체 생성자
@NoArgsConstructor //빈 생성자
@Data //getter, setter 생성
@Entity //db에 테이블을 생성
@Table( //두 컬럼에 같이 unique 제약조건을 걸기 위해서 이렇게 설정
        uniqueConstraints = { //유니크 제약조건
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId","userId"} //1번 유저가 1번 이미지를 좋아요하면 또 좋아할 수 없으므로 중복 유니크 체크
                )
        }
)
public class Likes { //N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; //1

    //오류 터지고 나서 잡는다
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; //1

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() { //LocalDateTime 자동으로 입력
        this.createDate = LocalDateTime.now();
    }
}
