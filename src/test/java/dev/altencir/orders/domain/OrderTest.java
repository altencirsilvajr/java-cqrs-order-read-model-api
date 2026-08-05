package dev.altencir.orders.domain;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal; import java.time.Instant; import java.util.List;
import org.junit.jupiter.api.Test;
class OrderTest {
 @Test void calculatesTotalWhenPlaced(){var order=Order.place("customer-1",List.of(new OrderItem("SKU-1",2,new BigDecimal("12.50"))),Instant.parse("2026-01-01T00:00:00Z"));assertThat(order.total()).isEqualByComparingTo("25.00");}
 @Test void rejectsEmptyOrder(){assertThatThrownBy(()->Order.place("customer-1",List.of(),Instant.now())).isInstanceOf(IllegalArgumentException.class);}
}
