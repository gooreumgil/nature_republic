package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.ItemSrc;
import com.daeboo.naturerepublic.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long reviewId;
    private String content;
    private String itemName;
    private int views;
    private LocalDateTime wroteAt;
    private ItemSrc itemSrc;
    private List<ItemSrc> reviewSrcs = new ArrayList<>();

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.itemName = review.getItem().getNameKor();
        this.views = review.getViews();
        this.wroteAt = review.getWroteAt();
        this.itemSrc = review.getItem().getItemSrcs().get(0);
        this.reviewSrcs = review.getItemSrcs();
    }
}
