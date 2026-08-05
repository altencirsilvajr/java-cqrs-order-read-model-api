package dev.altencir.orders.application;
import dev.altencir.orders.read.OrderSummaryRepository; import dev.altencir.orders.write.*; import org.springframework.stereotype.Service;
@Service public class ObservabilityService {
 private final OrderRepository orders; private final OutboxEventRepository outbox; private final OrderSummaryRepository summaries;
 public ObservabilityService(OrderRepository orders,OutboxEventRepository outbox,OrderSummaryRepository summaries){this.orders=orders;this.outbox=outbox;this.summaries=summaries;}
 public Overview overview(){return new Overview(orders.count(),outbox.countByPublishedAtIsNull(),outbox.countByPublishedAtIsNotNull(),summaries.count());}
 public record Overview(long acceptedOrders,long pendingOutbox,long publishedEvents,long projectedOrders){}
}
