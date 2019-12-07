package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.dto.QnaDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Qna {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Enumerated(EnumType.STRING)
    private QnaStatus qnaStatus;

    private String content;
    private boolean secretVal;
    private LocalDateTime wroteAt;
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public static Qna create(QnaDto.RequestForm qnaDto, Member member, Item item) {

        Qna qna = new Qna();
        qna.member = member;
        qna.item = item;
        qna.qnaStatus = QnaStatus.WAIT;
        qna.content = qnaDto.getContent();
        qna.secretVal = qnaDto.isSecretVal();
        qna.wroteAt = LocalDateTime.now();

        return qna;

    }

    public void setStatusComp() {
        this.qnaStatus = QnaStatus.COMP;
    }

    public void setStatusWait() {
        this.qnaStatus = QnaStatus.WAIT;
    }
}
