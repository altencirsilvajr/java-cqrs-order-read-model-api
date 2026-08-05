package dev.altencir.orders.domain;

import java.math.BigDecimal;

public record OrderItem(String sku, int quantity, BigDecimal unitPrice) {
    public OrderItem {
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("SKU is required");
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be positive");
        if (unitPrice == null || unitPrice.signum() < 0) throw new IllegalArgumentException("Unit price cannot be negative");
    }
    public BigDecimal subtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
