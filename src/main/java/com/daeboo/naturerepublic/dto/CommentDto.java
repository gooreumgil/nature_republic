package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Comment;
import com.daeboo.naturerepublic.domain.CommentType;
import com.daeboo.naturerepublic.domain.ItemSrc;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDto {

    @Getter
    @Setter
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RequestCommentUpdate {

        private Long commentId;
        private String content;

    }

    @Getter
    @Setter
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderReview {

        private Long itemId;
        private Long memberId;
        private String content;
        private CommentType commentType;
        private ItemDto.ReviewForm itemDto;
        private List<MultipartFile> itemSrcs = new ArrayList<>();
        private int rating;

    }
}
