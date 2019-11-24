package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.domain.embeded.Address;
import com.daeboo.naturerepublic.domain.embeded.Birthday;
import com.daeboo.naturerepublic.domain.embeded.PhoneNumber;
import lombok.*;

import javax.persistence.*;
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

    @Embedded
    private Address address;

    @Embedded
    private Birthday birthday;

    @Embedded
    private PhoneNumber phoneNumber;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RoleModel> roles = new HashSet<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders;


    public static Member createMember(String name, String password, String email, Address address, Birthday birthday, PhoneNumber phoneNumber) {
        Member member = new Member();
        member.name = name;
        member.password = password;
        member.email = email;
        member.address = address;
        member.birthday = birthday;
        member.phoneNumber = phoneNumber;

        return member;
    }
}
