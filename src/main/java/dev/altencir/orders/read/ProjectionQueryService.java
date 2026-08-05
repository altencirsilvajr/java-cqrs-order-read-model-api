package dev.altencir.orders.read;
import dev.altencir.orders.write.OrderRepository; import java.time.*; import java.util.*; import org.springframework.stereotype.Service;
@Service public class ProjectionQueryService {
 private final OrderRepository orders; private final OrderSummaryRepository summaries; private final Clock clock;
 public ProjectionQueryService(OrderRepository orders,OrderSummaryRepository summaries,Clock clock){this.orders=orders;this.summaries=summaries;this.clock=clock;}
 public Optional<OrderSummaryEntity> summary(UUID id){return summaries.findById(id);}
 public ProjectionStatus status(UUID id){var write=orders.findById(id);var read=summaries.findById(id);if(write.isEmpty()&&read.isEmpty())return new ProjectionStatus(id,"NOT_FOUND",null,null,null);if(read.isEmpty()){var accepted=write.orElseThrow().getPlacedAt();return new ProjectionStatus(id,"PENDING",accepted,null,Duration.between(accepted,clock.instant()).toMillis());}var summary=read.orElseThrow();var accepted=write.map(dev.altencir.orders.write.OrderEntity::getPlacedAt).orElse(summary.getSourceOccurredAt());var state=summary.getSourceOccurredAt().isBefore(accepted)?"STALE":"PROJECTED";return new ProjectionStatus(id,state,accepted,summary.getProjectedAt(),Math.max(0,Duration.between(accepted,summary.getProjectedAt()).toMillis()));}
 public record ProjectionStatus(UUID orderId,String state,Instant acceptedAt,Instant projectedAt,Long lagMilliseconds){}
}
