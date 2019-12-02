package com.daeboo.naturerepublic.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String title;
    private String content;
    private LocalDateTime wroteAt;
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "")
    private List<Comment> comments = new ArrayList<>();

}
