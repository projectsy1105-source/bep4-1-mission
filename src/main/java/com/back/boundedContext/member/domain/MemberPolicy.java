package com.back.boundedContext.member.domain;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MemberPolicy {
    private static final int PASSWORD_CHANGE_DAYS = 30;

    public Duration getPasswordChangeDuration() {
        return Duration.ofDays(PASSWORD_CHANGE_DAYS);
    }

    public int getPasswordChangeDays() {
        return PASSWORD_CHANGE_DAYS;
    }

    public boolean isNeedToChangePassword(LocalDateTime lastChangeDate) {
        if (lastChangeDate == null) return true;
        return lastChangeDate.plusDays(PASSWORD_CHANGE_DAYS)
                .isBefore(LocalDateTime.now());
    }

}
