package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Qna;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class QnaDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class MyPage {

        private Long id;
        private String content;
        private Long itemId;
        private String itemName;
        private String s3Key;

        public MyPage(Qna qna) {
            this.id = qna.getId();
            this.content = qna.getContent();
            this.itemId = qna.getItem().getId();
            this.itemName = qna.getItem().getNameKor();
            this.s3Key = qna.getItem().getItemSrcs().get(0).getS3Key();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class RequestForm {

        @NotNull @NotEmpty @NotBlank
        private String content;
        private boolean secretVal;
        private Long itemId;

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class ItemDetail {

        private Long id;
        private String content;
        private boolean secretVal;
        private Long itemId;
        private Long memberId;

        public ItemDetail(Qna qna) {
            this.id = qna.getId();
            this.content = qna.getContent();
            this.secretVal = qna.isSecretVal();
            this.itemId = qna.getItem().getId();
            this.memberId = qna.getMember().getId();
        }
    }

}
