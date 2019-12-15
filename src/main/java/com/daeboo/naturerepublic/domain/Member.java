package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String password;
    private String email;
    private Integer points;

    @Embedded
    private Address address;

    @Embedded
    private Birthday birthday;

    @Embedded
    private PhoneNumber phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleModel> roles = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qna> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<LikeReview> likeReviews = new ArrayList<>();

    public static Member createMember(String name, String password, String email, Address address, Birthday birthday, PhoneNumber phoneNumber, List<String> roles) {
        Member member = new Member();
        member.name = name;
        member.password = password;
        member.email = email;
        member.address = address;
        member.birthday = birthday;
        member.phoneNumber = phoneNumber;
        roles.forEach(roleModel -> {
            RoleModel createRoles = RoleModel.CreateRoles(member, roleModel);
            member.roles.add(createRoles);
        });

        return member;
    }

    public void minusPoints(Integer usePoints) {
        if (usePoints != null) {
            this.points -= usePoints;
        }
    }

    public void addPoints(Integer savePoints) {
        this.points += savePoints;
    }
}
