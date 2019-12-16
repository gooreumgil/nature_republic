package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Likes;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final ItemRepository itemRepository;

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

    public Page<Likes> findAllByMemberId(Long memberId, Pageable pageable) {
        return likesRepository.findAllByMemberId(memberId, pageable);
    }

    @Transactional
    public void removeByLikeId(List<Long> ids) {

        for (Long id : ids) {
            Likes likes = likesRepository.findById(id).get();
            Item item = itemRepository.findById(likes.getItem().getId()).get();
            item.minusLikes();
        }

        likesRepository.deleteAllByIdInQuery(ids);
    }

}
