package com.keb.fmhj.auth.service;

import com.keb.fmhj.auth.domain.CustomUserDetails;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<Member> userData = memberRepository.findByLoginId(loginId);

        if (userData.isEmpty()) {
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다: " + loginId);
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
        }

        return new CustomUserDetails(userData.get());
    }
}
