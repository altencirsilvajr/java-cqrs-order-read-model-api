package dev.altencir.orders.read;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="processed_events") public class ProcessedEventEntity { @Id private UUID eventId; @Column(nullable=false) private Instant processedAt; protected ProcessedEventEntity(){} public ProcessedEventEntity(UUID eventId,Instant processedAt){this.eventId=eventId;this.processedAt=processedAt;} public UUID getEventId(){return eventId;} }
