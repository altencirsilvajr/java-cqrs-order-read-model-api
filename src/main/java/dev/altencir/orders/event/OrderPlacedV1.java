package dev.altencir.orders.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderPlacedV1(UUID eventId, String eventType, int schemaVersion, UUID orderId, String customerId, int itemCount, BigDecimal total, Instant occurredAt) {
    public static final String TYPE = "OrderPlaced";
}
