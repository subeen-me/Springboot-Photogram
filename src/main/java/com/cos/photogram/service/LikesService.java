package com.cos.photogram.service;

import com.cos.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(int imageId, int principalId) {
        likesRepository.mLikes(imageId, principalId);
    }

    @Transactional
    public void unlikes(int imageId, int principalId) {
        likesRepository.mUnLikes(imageId, principalId);
    }
}
