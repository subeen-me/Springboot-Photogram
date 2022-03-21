package com.cos.photogram.service;

import com.cos.photogram.domain.subscribe.Subscribe;
import com.cos.photogram.domain.subscribe.SubscribeRepository;
import com.cos.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

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
