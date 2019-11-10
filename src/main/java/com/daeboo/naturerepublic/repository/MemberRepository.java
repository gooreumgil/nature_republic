package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
