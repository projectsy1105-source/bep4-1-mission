package com.back.boundedContext.market.app;


import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.global.global.RsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {

    private final MarketSupport marketSupport;
    private final MarketSyncMemberUseCase marketSyncMemberUseCase;
    private final MarketCreateProductUseCase marketCreateProductUseCase;
    private final MarketCreateCartUseCase marketCreateCartUseCase;
    private final MarketCreateOrderUseCase marketCreateOrderUseCase;
    private final MarketCompleteOrderRequestPaymentUseCase marketCompleteOrderRequestPaymentUseCase;
    private final MarketCancelOrderRequestPaymentUseCase marketCancelOrderRequestPaymentUseCase;

    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.productsCount();
    }

    @Transactional(readOnly = true)
    public long ordersCount() {
        return marketSupport.ordersCount();
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return marketSupport.findCartByBuyer(buyer);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(int id) {
        return marketSupport.findProductById(id);
    }

    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public Product createProduct(MarketMember seller, String sourceTypeCode, int sourceId, String name, String description, BigDecimal price, BigDecimal salePrice) {
        return marketCreateProductUseCase.createProduct(seller, sourceTypeCode, sourceId, name, description, price, salePrice);
    }

    @Transactional
    public RsData<Cart> createCart(MemberDto buyer) {
        return marketCreateCartUseCase.createCart(buyer);
    }

    @Transactional
    public RsData<Order> createOrder(Cart cart) {
        return marketCreateOrderUseCase.createOrder(cart);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(int orderId) {
        return marketSupport.findOrderById(orderId);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllReadyForPayment() {
        return marketSupport.findAllReadyForPayment();
    }

    @Transactional
    public void requestPayment(Order order, BigDecimal pgPaymentAmount) {
        order.requestPayment(pgPaymentAmount);
    }

    @Transactional
    public void completeOrderPayment(int id) {
        marketCompleteOrderRequestPaymentUseCase.handle(id);
    }

    @Transactional
    public void cancelOrderPayment(int id) {
        marketCancelOrderRequestPaymentUseCase.handle(id);
    }

}
