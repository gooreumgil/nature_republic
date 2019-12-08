package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Comment;
import com.daeboo.naturerepublic.domain.Qna;
import com.daeboo.naturerepublic.domain.QnaStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QnaDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class MyPage {

        private Long id;
        private String content;
        private LocalDateTime wroteAt;
        private Long itemId;
        private String itemName;
        private String s3Key;
        private List<CommentDto.MyPageQnaComment> commentList = new ArrayList<>();

        public MyPage(Qna qna) {
            this.id = qna.getId();
            this.content = qna.getContent();
            this.wroteAt = qna.getWroteAt();
            this.itemId = qna.getItem().getId();
            this.itemName = qna.getItem().getNameKor();
            this.s3Key = qna.getItem().getItemSrcs().get(0).getS3Key();
            this.commentList = qna.getComments().stream().map(CommentDto.MyPageQnaComment::new).collect(Collectors.toList());
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
        private String qnaStatus;
        private Long memberId;
        private String memberName;
        private LocalDateTime wroteAt;
        private List<Comment> comments = new ArrayList<>();

        public ItemDetail(Qna qna) {
            this.id = qna.getId();
            this.content = qna.getContent();
            this.secretVal = qna.isSecretVal();
            this.qnaStatus = qna.getQnaStatus().toString();
            this.memberId = qna.getMember().getId();
            this.memberName = qna.getMember().getName();
            this.wroteAt = qna.getWroteAt();
            this.comments = qna.getComments();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class RequestComment {

        @NotNull
        private Long memberId;
        @NotNull
        private Long itemId;
        @NotNull
        private Long qnaId;
        @NotNull @NotEmpty @NotBlank
        private String content;

    }

}
