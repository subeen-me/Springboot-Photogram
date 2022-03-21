package com.cos.photogram.domain.subscribe;

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
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {"fromUserId","toUserId"}
                )
        }
)
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략을 DB를 따라간다
    private int id;

    @JoinColumn(name = "fromUserId") //카멜표기법으로 컬럼명 변경
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() { //LocalDateTime 자동으로 입력
        this.createDate = LocalDateTime.now();
    }

}
