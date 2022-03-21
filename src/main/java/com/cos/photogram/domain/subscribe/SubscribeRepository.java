package com.cos.photogram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying //insert, delete, update를 네이티브 쿼리로 작성할 때 해당 어노테이션이 필요하다
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId); //성공시 1, 실패시 -1. 인서트를 10번 하면 10이 리턴(변경된 행의 갯수만큼 숫자가 리턴)

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId =:fromUserId AND :toUserId" , nativeQuery = true)
    void mUnsubscribe(int fromUserId, int toUserId); //성공시 1, 실패시 -1
}
