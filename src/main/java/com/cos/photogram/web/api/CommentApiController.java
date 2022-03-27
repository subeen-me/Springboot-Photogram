package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.comment.Comment;
import com.cos.photogram.service.CommentService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment") //json 데이터를 받기 위해 @RequestBody 어노테이션
    public ResponseEntity<?> commentSave(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Comment comment = commentService.writeComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId()); //content, imageId, userId를 전달해줘야한다
        return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기 성공",comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new CMRespDto<>(1,"댓글삭제 성공",null), HttpStatus.OK);
    }
}
