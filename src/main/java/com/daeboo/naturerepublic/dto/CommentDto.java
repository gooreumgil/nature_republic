package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class ItemReview {

        private Long id;
        private String content;
        private LocalDateTime wroteAt;
        private Long memberId;
        private String memberName;

        public ItemReview(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.wroteAt = comment.getWroteAt();
            this.memberId = comment.getMember().getId();
            this.memberName = comment.getMember().getName();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class RequestCommentUpdate {

        private Long commentId;
        private String content;

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class MyPageQnaComment {

        private Long commentId;
        private String reply;
        private LocalDateTime wroteAt;

        public MyPageQnaComment(Comment comment) {
            this.commentId = comment.getId();
            this.reply = comment.getContent();
            this.wroteAt = comment.getWroteAt();
        }
    }

}
