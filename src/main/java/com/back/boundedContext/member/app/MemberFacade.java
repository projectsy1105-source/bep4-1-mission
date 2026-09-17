package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.exception.DomainException;
import com.back.boundedContext.member.out.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberRepository memberRepository;
    private final MemberJoinUseCase memberJoinUseCase;

    public long count() {
        return memberRepository.count();
    }

    public Member join(String username, String password, String nickname) {
        return memberJoinUseCase.join(username, password, nickname);
    }

    public Optional<Member> findById(int id) { return memberRepository.findById(id); }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
