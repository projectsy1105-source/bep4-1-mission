package com.back.boundedContext.market.domain;

import com.back.shared.member.domain.ReplicaMember;
import com.back.shared.member.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "MARKET_MEMBER")
@Getter
@NoArgsConstructor
public class MarketMember extends ReplicaMember {
    public MarketMember(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String password, String nickname, int activityScore) {
        super(id, createDate, modifyDate, username, password, nickname, activityScore);
    }

    public MemberDto toDto() {
        return new MemberDto(getId(), getCreateDate(), getModifyDate(), getUsername(), getNickname(), getActivityScore());
    }
}