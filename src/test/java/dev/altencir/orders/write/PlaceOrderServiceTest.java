package dev.altencir.orders.write;
import static org.assertj.core.api.Assertions.*; import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper; import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.altencir.orders.domain.OrderItem; import java.math.BigDecimal; import java.time.*; import java.util.List;
import org.junit.jupiter.api.Test;
class PlaceOrderServiceTest {
 @Test void persistsOrderAndOutboxAsOneUseCase(){var orders=mock(OrderRepository.class);var outbox=mock(OutboxEventRepository.class);var json=new ObjectMapper().registerModule(new JavaTimeModule());var clock=Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"),ZoneOffset.UTC);var service=new PlaceOrderService(orders,outbox,json,clock);var result=service.place("customer-1",List.of(new OrderItem("SKU-1",1,new BigDecimal("10.00"))));assertThat(result.acceptedAt()).isEqualTo(clock.instant());verify(orders).save(any(OrderEntity.class));verify(outbox).save(argThat(e->e.getAggregateId().equals(result.orderId())&&e.getPayload().contains("OrderPlaced")));}
}
