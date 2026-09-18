package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final WalletRepository walletRepository;
    private final CashMemberRepository cashMemberRepository;

    public Wallet createWallet(MemberDto holder) {
        CashMember member = cashMemberRepository.getReferenceById(holder.getId());
        Wallet wallet = new Wallet(member);

        return walletRepository.save(wallet);
    }
}
