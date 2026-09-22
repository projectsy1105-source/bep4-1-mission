package com.back.boundedContext.payout.domain;

import com.back.shared.member.domain.ReplicaMember;
import com.back.shared.member.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAYOUT_MEMBER")
@NoArgsConstructor
@Getter
public class PayoutMember extends ReplicaMember {

    public PayoutMember(int id, LocalDateTime createDate, LocalDateTime modifyDate, String username, String nickname, int activityScore) {
        super(id, createDate, modifyDate, username, "", nickname, activityScore);
    }

    public MemberDto toDto() {
        return new MemberDto(getId(), getCreateDate(), getModifyDate(), getUsername(), getNickname(), getActivityScore());
    }

}
