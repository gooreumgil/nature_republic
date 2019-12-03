package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Likes;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Likes findByMemberIdAndItemId(Long memberId, Long itemId) {

        Optional<Likes> optionalLikes = likesRepository.findByMemberIdAndItemId(memberId, itemId);
        if (optionalLikes.isPresent()) {
            return optionalLikes.get();
        } else {
            return null;
        }
    }

    @Transactional
    public void remove(Long memberId, Item item) {
        Optional<Likes> optionalLikes = likesRepository.findByMemberIdAndItemId(memberId, item.getId());
        if (optionalLikes.isPresent()) {
            Likes likes = optionalLikes.get();
            likesRepository.deleteById(likes.getId());
        }

        item.minusLikes();

    }

    public List<Likes> findAllByMemberId(Long memberId) {
        return likesRepository.findAllByMemberId(memberId);
    }
}
