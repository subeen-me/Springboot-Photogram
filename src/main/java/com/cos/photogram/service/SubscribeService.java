package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.SubscribeRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import com.cos.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; //모든 Repository는 EntityManager를 구현해서 만들어져있는 구현체.

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {

        //DB에서 select해서 subscribeDto의 형태로 받아오면 된다.
        //쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId= ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); //끝에 세미콜론 첨부하면 안된다.


        //1.물음표 principalId
        //2.물음표 principalId(로그인한 아이디)
        //3.마지막 물픔표 pageUserId

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        //쿼리 실행
        //  JpaResultMapper result = new JpaResultMapper();
        //  List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
        List<Object[]> results = query.getResultList();
        List<SubscribeDto> subscribeDtos = results.stream()
                .map(o -> new SubscribeDto(o))
                .collect(Collectors.toList());

        //구독리스트 리턴
        return subscribeDtos;
    }

    @Transactional
    public void subscribe(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 사용자입니다.");
        }
    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId) {
        subscribeRepository.mUnsubscribe(fromUserId, toUserId);
    }


}
