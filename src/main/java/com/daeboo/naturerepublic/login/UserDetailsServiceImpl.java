package com.daeboo.naturerepublic.login;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.RoleModel;
import com.daeboo.naturerepublic.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String[] roles = member.getRoles().stream().map(RoleModel::getName).toArray(String[]::new);

            return User.builder()
                    .username(member.getName())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .roles(roles)
                    .build();
        } else {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
