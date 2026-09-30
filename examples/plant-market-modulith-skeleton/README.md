# Spring Modulith Skeleton — Public API 호출 vs Event

이 예제는 **모듈 간 통신을 전부 이벤트로 해야 하는가?** 를 비교하기 위한 최소 뼈대입니다.

## 1. 즉시 결과가 필요한 흐름 — 동기 Public API

```text
Order
  |
  | InventoryUseCase.reserve()
  v
Product
  |
  +--> success / fail 즉시 반환
```

Checkout은 지금 이 상품을 살 수 있는지 즉시 알아야 하므로 Product의 공개 API를 동기 호출합니다.
Order는 `PlantItem`, `PlantItemRepository`, Product 내부 Service를 모릅니다.

이 흐름까지 이벤트로 만들면 Order가 결과 이벤트를 기다리기 위해 correlationId, timeout, 중간상태, 재시도, 중복처리 전략을 추가로 가져야 합니다.

## 2. 반환값이 필요 없는 후속 처리 — Event

```text
Order
  |
  | PurchaseConfirmedEvent
  +----------------------+
                         |
                         v
                    Settlement
```

구매확정은 이미 발생한 사실이므로 Order가 Settlement의 행위를 직접 지시하지 않습니다.
Settlement는 `PurchaseConfirmedEvent`를 구독해 자기 책임을 수행합니다.

## 판단 기준

### 동기 Public API가 어울리는 경우
- 현재 UseCase가 결과를 즉시 필요로 함
- 성공/실패가 다음 로직을 결정함
- Query 또는 즉시 판정이 필요한 Command

예: 재고 선점, 판매 가능 여부, 권한 조회

### Event가 어울리는 경우
- 이미 일어난 사실을 알림
- Producer가 Consumer의 후속 행동을 몰라도 됨
- 현재 HTTP 요청에서 결과를 기다릴 필요가 없음

예: PurchaseConfirmed, PaymentCompleted, PaymentFailed, RefundCompleted

## 핵심

`다른 모듈의 공개 API에 의존`하는 것과 `다른 모듈의 내부 구현에 강결합`되는 것은 다릅니다.

나쁜 예:
```java
PlantItem item = plantItemRepository.findById(...);
item.reserve();
```

허용할 예:
```java
private final InventoryUseCase inventoryUseCase;
```

Spring Modulith는 모듈의 공개 API bean dependency와 application event를 모두 정상적인 module interaction으로 다룰 수 있고, `ApplicationModules.verify()`로 경계 위반을 검증할 수 있습니다.

## 구조

```text
com.example.plantmarket
├─ product
│  ├─ InventoryUseCase.java
│  ├─ ReservationResult.java
│  └─ internal
│     └─ InMemoryInventoryService.java
├─ order
│  ├─ PurchaseConfirmedEvent.java
│  └─ internal
│     ├─ CheckoutService.java
│     └─ OrderController.java
└─ settlement
   └─ internal
      └─ PurchaseConfirmedListener.java
```

## 실행

Repository root에서:

```bash
./gradlew -p examples/plant-market-modulith-skeleton test
./gradlew -p examples/plant-market-modulith-skeleton bootRun
```

독립 ZIP으로 받은 경우 Gradle wrapper가 없으므로 IDE에서 Gradle project로 열거나 로컬 Gradle을 사용하세요.

### 재고 선점

```bash
curl -X POST "http://localhost:8080/orders/1/checkout?plantItemId=100"
```

같은 item을 다시 요청하면 `ALREADY_RESERVED`가 반환됩니다.

### 구매확정 Event

```bash
curl -X POST "http://localhost:8080/orders/1/confirm?sellerId=10&amount=50000"
```

Settlement listener가 이벤트를 받아 로그를 남깁니다.

## 실제 프로젝트에서

이 예제는 흐름을 보기 쉽게 하려고 `@EventListener`만 사용했습니다.
DB 트랜잭션 이후 안전한 모듈 이벤트 처리에는 Spring Modulith의 Event Publication Registry와 `@ApplicationModuleListener`를 검토할 수 있습니다.
Kafka를 붙일 때도 Domain 코드가 `KafkaTemplate`을 직접 알기보다는 외부화할 이벤트를 분리하는 방향이 좋습니다.
