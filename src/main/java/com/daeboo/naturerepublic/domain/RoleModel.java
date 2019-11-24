package com.daeboo.naturerepublic.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "role")
@ToString
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class RoleModel {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}
