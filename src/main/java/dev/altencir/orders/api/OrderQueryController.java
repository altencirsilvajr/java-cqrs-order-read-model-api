package dev.altencir.orders.api;
import dev.altencir.orders.read.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders") public class OrderQueryController {
 private final ProjectionQueryService queries; public OrderQueryController(ProjectionQueryService queries){this.queries=queries;}
 @GetMapping("/{id}") ResponseEntity<OrderSummaryResponse> summary(@PathVariable UUID id){return queries.summary(id).map(s->ResponseEntity.ok(new OrderSummaryResponse(s.getOrderId(),s.getCustomerId(),s.getItemCount(),s.getTotal(),s.getSourceEventId(),s.getSourceOccurredAt(),s.getProjectedAt()))).orElseGet(()->ResponseEntity.notFound().build());}
 @GetMapping("/{id}/projection-status") ResponseEntity<ProjectionQueryService.ProjectionStatus> status(@PathVariable UUID id){var status=queries.status(id);return "NOT_FOUND".equals(status.state())?ResponseEntity.notFound().build():ResponseEntity.ok(status);}
 record OrderSummaryResponse(UUID orderId,String customerId,int itemCount,BigDecimal total,UUID sourceEventId,Instant sourceOccurredAt,Instant projectedAt){}
}
