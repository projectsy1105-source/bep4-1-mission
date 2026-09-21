package com.back.shared.market.out;

import com.back.global.exception.DomainException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class TossPaymentsService {

    private static final String TOSS_BASE_URL =  "https://api.tosspayments.com";
    private static final String CONFIRM_PATH = "/v1/payments/confirm";

    private final RestClient tossRestClient;
    private final ObjectMapper objectMapper;

    @Value("${custom.market.toss.payments.secretKey}")
    private String tossSecretKey;

    public TossPaymentsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.tossRestClient = RestClient.builder()
                .baseUrl(TOSS_BASE_URL)
                .build();
    }

    public Map<String, Object> confirmCardPayment(String paymentKey, String orderId, BigDecimal amount) {
        TossPaymentsConfirmRequest requestBody = new TossPaymentsConfirmRequest(paymentKey, orderId, amount);

        try {
            ResponseEntity<Map> responseEntity = createConfirmRequest(requestBody)
                    .retrieve()
                    .toEntity(Map.class);

            int httpStatus = responseEntity.getStatusCode().value();
            Map<String, Object> responseBody = responseEntity.getBody();

            if (httpStatus != 200) {
                throw createDomainExceptionFromNon200(httpStatus, responseBody);
            }

            if (responseBody == null) {
                throw new DomainException("400-EMPTY_RESPONSE", "토스 결제 승인 응답 바디가 비었습니다.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> casted = (Map<String, Object>) responseBody;
            return casted;
        } catch (RestClientResponseException e) {
            throw createDomainExceptionFromHttpError(e);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new DomainException("400-TOSS_CALL_EXCEPTION", "토스 결제 승인 호출 중 예외: " + e.getMessage());
        }
    }

    private RestClient.RequestHeadersSpec<?> createConfirmRequest(TossPaymentsConfirmRequest requestBody) {
        return tossRestClient.post()
                .uri(CONFIRM_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth(tossSecretKey, ""))
                .body(requestBody);
    }

    private DomainException createDomainExceptionFromNon200(int httpStatus, Map responseBody) {
        if (responseBody == null) {
            return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패(응답 바디 없음), HTTP " + httpStatus);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) responseBody;

        String code = extractStringOrDefault(body, "code", "HTTP_" + httpStatus);
        String message = extractStringOrDefault(body, "message", "토스 결제 승인 실패, HTTP " + httpStatus);

        return new DomainException("400-" + code, message);
    }

    private DomainException createDomainExceptionFromHttpError(RestClientResponseException e) {

        int httpStatus = e.getStatusCode().value();
        String rawBody = e.getResponseBodyAsString(StandardCharsets.UTF_8);

        if (rawBody == null || rawBody.isBlank()) {
            return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패(빈 바디), HTTP " + httpStatus);
        }

        try {
            Map<String, Object> body = objectMapper.readValue(rawBody, new TypeReference<>() {});
            String code = extractStringOrDefault(body, "code", "HTTP_" + httpStatus);
            String message = extractStringOrDefault(body, "message", "토스 결제 승인 실패, HTTP " + httpStatus);
            return new DomainException("400-" + code, message);
        } catch (Exception ex) {
            throw new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패, HTTP " + httpStatus + " / body=" + rawBody);
        }

    }

    private String extractStringOrDefault(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        if (value instanceof String s && !s.isBlank()) return s;

        return defaultValue;
    }


    public record TossPaymentsConfirmRequest (@NotBlank String paymentKey, @NotBlank String orderId, BigDecimal amount) {}
}
