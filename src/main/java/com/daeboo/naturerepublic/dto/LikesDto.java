package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class LikesDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LikePage {

        private Long id;
        private Long itemId;
        private String s3Key;
        private String itemName;
        private int itemPrice;

        public LikePage(Likes likes) {
            this.id = likes.getId();
            this.itemId = likes.getItem().getId();
            this.s3Key = likes.getItem().getItemSrcs().get(0).getS3Key();
            this.itemName = likes.getItem().getNameKor();
            this.itemPrice = likes.getItem().getPrice();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete {

        private List<Long> likeIds = new ArrayList<>();

    }

}
