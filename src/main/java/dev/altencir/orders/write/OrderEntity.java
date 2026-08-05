package dev.altencir.orders.write;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "orders")
public class OrderEntity {
    @Id private UUID id;
    @Column(nullable=false) private String customerId;
    @Column(nullable=false, precision=19, scale=2) private BigDecimal total;
    @Column(nullable=false) private int itemCount;
    @Column(nullable=false) private Instant placedAt;
    protected OrderEntity() {}
    public OrderEntity(UUID id, String customerId, BigDecimal total, int itemCount, Instant placedAt) { this.id=id; this.customerId=customerId; this.total=total; this.itemCount=itemCount; this.placedAt=placedAt; }
    public UUID getId(){return id;} public String getCustomerId(){return customerId;} public BigDecimal getTotal(){return total;} public int getItemCount(){return itemCount;} public Instant getPlacedAt(){return placedAt;}
}
