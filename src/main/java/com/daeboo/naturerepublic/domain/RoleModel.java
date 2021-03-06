package com.daeboo.naturerepublic.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleModel {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public RoleModel(Member member, RoleEnum name) {
        this.member = member;
        this.name = name;
    }

    public static RoleModel CreateRoles(Member member, String role) {
        RoleModel roleModel = new RoleModel();
        roleModel.member = member;
        if (role.equals("ADMIN")) {
            roleModel.name = RoleEnum.ADMIN;
        } else {
            roleModel.name = RoleEnum.USER;
        }

        return roleModel;
    }
}
