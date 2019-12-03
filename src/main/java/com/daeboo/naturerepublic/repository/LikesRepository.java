package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMemberIdAndItemId(Long memberId, Long itemId);
    List<Likes> findAllByMemberId(Long memberId);

}
