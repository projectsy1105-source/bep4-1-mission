package com.back.boundedContext.market.in;

import com.back.global.exception.DomainException;
import com.back.global.global.RsData.RsData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/market/toss-payments")
public class ApiV1TossPaymentsConfigController {

    private final String clientKey;

    public ApiV1TossPaymentsConfigController(
            @Value("${custom.market.toss.payments.clientKey}") String clientKey
    ) {
        this.clientKey = clientKey;
    }

    @GetMapping("/client-key")
    public RsData<ClientKeyResponse> getClientKey() {
        if (clientKey.isBlank()) {
            throw new DomainException("500-TOSS_CLIENT_KEY_NOT_CONFIGURED", "TOSS_PAYMENTS_CLIENT_KEY가 설정되지 않았습니다.");
        }

        return new RsData<>("200-1", "토스페이먼츠 클라이언트 키 조회 성공", new ClientKeyResponse(clientKey));
    }

    public record ClientKeyResponse(String clientKey) {}
}
