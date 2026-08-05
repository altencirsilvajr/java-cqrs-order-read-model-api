package dev.altencir.orders.api;
import dev.altencir.orders.read.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders") public class OrderQueryController {
 private final ProjectionQueryService queries; public OrderQueryController(ProjectionQueryService queries){this.queries=queries;}
 @GetMapping("/{id}") OrderSummaryResponse summary(@PathVariable UUID id){var s=queries.summary(id).orElseThrow(()->new ResourceNotFoundException("Order projection "+id+" is not available"));return new OrderSummaryResponse(s.getOrderId(),s.getCustomerId(),s.getItemCount(),s.getTotal(),s.getSourceEventId(),s.getSourceOccurredAt(),s.getProjectedAt());}
 @GetMapping("/{id}/projection-status") ProjectionQueryService.ProjectionStatus status(@PathVariable UUID id){var status=queries.status(id);if("NOT_FOUND".equals(status.state()))throw new ResourceNotFoundException("Order "+id+" was not found");return status;}
 record OrderSummaryResponse(UUID orderId,String customerId,int itemCount,BigDecimal total,UUID sourceEventId,Instant sourceOccurredAt,Instant projectedAt){}
}
