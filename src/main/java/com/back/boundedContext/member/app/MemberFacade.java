package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.domain.MemberPolicy;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberRepository memberRepository;
    private final MemberJoinUseCase memberJoinUseCase;
    private final MemberPolicy memberPolicy;

    public long count() {
        return memberRepository.count();
    }

    public RsData<Member> join(String username, String password, String nickname) {
        return memberJoinUseCase.join(username, password, nickname);
    }

    public String randomTip() {
//        public String randomTip(int memberId) {
//        Member member = memberRepository.findById(memberId).get();
//        int dday = memberPolicy.getPasswordChangeDays() - Period.between(member.getModifyDate().toLocalDate(), LocalDate.now()).getDays();
        return "비밀번호의 유효기간은 %d일 입니다.".formatted(memberPolicy.getPasswordChangeDays());
    }

    public Optional<Member> findById(int id) { return memberRepository.findById(id); }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
