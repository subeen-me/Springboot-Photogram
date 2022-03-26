package com.cos.photogram.domain.Image;

import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.likes.Likes;
import com.cos.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnoreProperties({"images"}) //user의 images는 또 필요없기 때문에
    @JoinColumn(name = "userId") //객체를 db에 저장할 수 없기때문에 폴인키가 들어간다. 그 컬럼명
    @ManyToOne
    private User user; // 1, 1

    //이미지 좋아요, 댓글 추후 업데이트
    @JsonIgnoreProperties({"image"}) //likes가 리턴될 때 image가 리턴되지 않게 하기위해(무한참조 방지)
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    private LocalDateTime createDate; //db에는 항상 시간이 들어가야한다.

    //댓글 양방향 매핑, 무한참조 방지
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image") //comments의 폴인키의 자바변수를 적어준다.
    private List<Comment> comments;

    @Transient //DB에 컬럼이 만들어지지 않는다
    private boolean likeState;

    @Transient
    private int likeCount;

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
