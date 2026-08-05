package dev.altencir.orders.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Order(UUID id, String customerId, List<OrderItem> items, BigDecimal total, Instant placedAt) {
    public static Order place(String customerId, List<OrderItem> items, Instant now) {
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("An order needs at least one item");
        var total = items.stream().map(OrderItem::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Order(UUID.randomUUID(), customerId, List.copyOf(items), total, now);
    }
}
