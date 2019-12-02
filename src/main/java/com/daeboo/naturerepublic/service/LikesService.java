package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Likes;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void addLikes(Item item, Member member) {

        Likes likes = Likes.CreateLikes(item, member);
        likesRepository.save(likes);

    }

}
