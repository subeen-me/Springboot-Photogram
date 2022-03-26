package com.cos.photogram.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Query(value = "INSERT INTO comment(content, imageId, userId, createData) VALUES(:imageId, :userId, now())", nativeQuery = true)
    Comment mSave(String content, int imageId, int userId);
}
