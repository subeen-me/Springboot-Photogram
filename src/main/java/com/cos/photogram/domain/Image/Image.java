package com.cos.photogram.domain.Image;

import com.cos.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder //dto에서 빌더패턴을 이용하기 위해 사용. toEntity 메서드
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //db에 테이블을 생성
public class Image { // N, 1

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption; //내용
    private String postImageUrl; //사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 - DB에 그 저장된 경로를 insert한다

    @JoinColumn(name = "userId") //객체를 db에 저장할 수 없기때문에 폴인키가 들어간다. 그 컬럼명
    @ManyToOne
    private User user; // 1, 1

    //이미지 좋아요, 댓글 추후 업데이트

    private LocalDateTime createDate; //db에는 항상 시간이 들어가야한다.

    @PrePersist
    public void createDate() { //LocalDateTime 자동으로 입력
        this.createDate = LocalDateTime.now();
    }

    // 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 함
//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\'' +
//                ", createDate=" + createDate +
//                '}';
//    }
}
