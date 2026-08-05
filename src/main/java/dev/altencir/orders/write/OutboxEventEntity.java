package dev.altencir.orders.write;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="outbox_events")
public class OutboxEventEntity {
    @Id private UUID id;
    @Column(nullable=false) private UUID aggregateId;
    @Column(nullable=false) private String eventType;
    @Column(nullable=false, columnDefinition="text") private String payload;
    @Column(nullable=false) private Instant occurredAt;
    private Instant publishedAt;
    protected OutboxEventEntity() {}
    public OutboxEventEntity(UUID id, UUID aggregateId, String eventType, String payload, Instant occurredAt){this.id=id;this.aggregateId=aggregateId;this.eventType=eventType;this.payload=payload;this.occurredAt=occurredAt;}
    public void markPublished(Instant at){publishedAt=at;}
    public UUID getId(){return id;} public UUID getAggregateId(){return aggregateId;} public String getEventType(){return eventType;} public String getPayload(){return payload;} public Instant getOccurredAt(){return occurredAt;} public Instant getPublishedAt(){return publishedAt;}
}
