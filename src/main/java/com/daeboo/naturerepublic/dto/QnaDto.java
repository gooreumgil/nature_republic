package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Qna;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QnaDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class MyPage {

        private Long id;
        private String title;
        private String content;
        private Long itemId;
        private String itemName;
        private String s3Key;

        public MyPage(Qna qna) {
            this.id = qna.getId();
            this.title = qna.getTitle();
            this.content = qna.getContent();
            this.itemId = qna.getItem().getId();
            this.itemName = qna.getItem().getNameKor();
            this.s3Key = qna.getItem().getItemSrcs().get(0).getS3Key();
        }
    }

}
