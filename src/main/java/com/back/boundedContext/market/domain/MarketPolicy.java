package com.back.boundedContext.market.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Configuration
public class MarketPolicy {
    public static double PRODUCT_PAYOUT_RATE;

    @Value("${custom.market.product.payoutRate}")
    public void setProductPayoutRate(double productPayoutRate) {
        PRODUCT_PAYOUT_RATE = productPayoutRate;
    }

    public static BigDecimal calculatePayoutFee(BigDecimal salePrice, double payoutRate) {
        return salePrice.subtract(calculateSalePriceWithoutFee(salePrice, payoutRate));
    }

    public static BigDecimal calculateSalePriceWithoutFee(BigDecimal salePrice, double payoutRate) {
        return salePrice.multiply(BigDecimal.valueOf(payoutRate)).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);

    }

}
