package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.exception.EmailNotExistedException;
import com.daeboo.naturerepublic.exception.PasswordWrongException;
import com.daeboo.naturerepublic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);

    }

    public List<MemberDto.ListView> findAll() {

        List<Member> memberAll = memberRepository.findAll();

        List<MemberDto.ListView> result = memberAll.stream().map(member -> {
            return new MemberDto.ListView(member);
        }).collect(Collectors.toList());

        return result;
    }

}
