package com.back.boundedContext.member.app;

import com.back.boundedContext.member.domain.MemberPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGetRandomSecureTipUseCase {

    private final MemberPolicy memberPolicy;

    public String randomTip() {
//        public String randomTip(int memberId) {
//        Member member = memberRepository.findById(memberId).get();
//        int dday = memberPolicy.getPasswordChangeDays() - Period.between(member.getModifyDate().toLocalDate(), LocalDate.now()).getDays();
        return "비밀번호의 유효기간은 %d일 입니다.".formatted(memberPolicy.getPasswordChangeDays());
    }

}
