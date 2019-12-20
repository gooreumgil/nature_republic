package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.exception.EmailNotExistedException;
import com.daeboo.naturerepublic.exception.PasswordWrongException;
import com.daeboo.naturerepublic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.expression.ExpressionException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void update(MemberDto.Update memberDto) {
        Member member = memberRepository.findById(memberDto.getId()).get();

        String password = member.getPassword();
        String passwordConfirm = memberDto.getPasswordConfirm();

        if (!password.equals(passwordConfirm)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        member.update(memberDto);
    }

    public List<MemberDto.ListView> findAll() {

        List<Member> memberAll = memberRepository.findAll();
        List<MemberDto.ListView> result = memberAll.stream().map(MemberDto.ListView::new).collect(Collectors.toList());

        return result;
    }



    public Member findByName(String name) {
//        memberRepository.findByName(name).orElseThrow(() -> throw new UsernameNotFoundException("존재하지 않다"));
        Member member = memberRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("no exist"));
        return member;
    }

    public Member findbyId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return member;
    }



}
