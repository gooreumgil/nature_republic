package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMemberIdAndItemId(Long memberId, Long itemId);

    List<Likes> findAllByMemberId(Long memberId);

    @Modifying
    @Query("delete from Likes li where li.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

}
