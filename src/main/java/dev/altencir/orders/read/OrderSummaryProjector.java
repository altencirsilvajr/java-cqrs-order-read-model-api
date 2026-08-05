package dev.altencir.orders.read;
import com.fasterxml.jackson.databind.ObjectMapper; import dev.altencir.orders.event.OrderPlacedV1; import io.micrometer.core.instrument.MeterRegistry; import java.time.Clock;
import org.springframework.kafka.annotation.KafkaListener; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional;
@Component public class OrderSummaryProjector {
 private final OrderSummaryRepository summaries; private final ProcessedEventRepository processed; private final ObjectMapper json; private final Clock clock; private final MeterRegistry metrics;
 public OrderSummaryProjector(OrderSummaryRepository summaries,ProcessedEventRepository processed,ObjectMapper json,Clock clock,MeterRegistry metrics){this.summaries=summaries;this.processed=processed;this.json=json;this.clock=clock;this.metrics=metrics;}
 @KafkaListener(topics="${app.kafka.topic}") @Transactional("readTransactionManager")
 public void project(String payload){try{var event=json.readValue(payload,OrderPlacedV1.class);if(processed.existsById(event.eventId())){metrics.counter("orders.projection.duplicates").increment();return;}summaries.save(new OrderSummaryEntity(event.orderId(),event.customerId(),event.itemCount(),event.total(),event.eventId(),event.occurredAt(),clock.instant()));processed.save(new ProcessedEventEntity(event.eventId(),clock.instant()));metrics.counter("orders.projection.applied").increment();}catch(com.fasterxml.jackson.core.JsonProcessingException ex){throw new IllegalArgumentException("Unsupported OrderPlaced payload",ex);}}
}
