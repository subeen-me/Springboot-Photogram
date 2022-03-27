package com.cos.photogram.service;


import com.cos.photogram.domain.Image.Image;
import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.domain.comment.CommentRepository;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import com.cos.photogram.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment writeComment(String content, int imageId, int userId) {

        //객체를 만들 때 id값만 담아서 insert 할 수 있다. 가짜 객체를 만든다
        //대신 return 시에 image 객체와 user객체는 id값만 가지고 있는 빈 객체를 리턴받는다.
        Image image = new Image();
        image.setId(imageId);

        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(int id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
