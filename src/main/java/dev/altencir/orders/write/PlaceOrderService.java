package dev.altencir.orders.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.altencir.orders.domain.Order;
import dev.altencir.orders.domain.OrderItem;
import dev.altencir.orders.event.OrderPlacedV1;
import java.time.Clock;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceOrderService {
    private final OrderRepository orders; private final OutboxEventRepository outbox; private final ObjectMapper json; private final Clock clock;
    public PlaceOrderService(OrderRepository orders, OutboxEventRepository outbox, ObjectMapper json){this(orders,outbox,json,Clock.systemUTC());}
    PlaceOrderService(OrderRepository orders, OutboxEventRepository outbox, ObjectMapper json, Clock clock){this.orders=orders;this.outbox=outbox;this.json=json;this.clock=clock;}
    @Transactional("writeTransactionManager")
    public PlacedOrder place(String customerId, List<OrderItem> items) {
        var order=Order.place(customerId,items,clock.instant());
        orders.save(new OrderEntity(order.id(),order.customerId(),order.total(),order.items().size(),order.placedAt()));
        var event=new OrderPlacedV1(UUID.randomUUID(),OrderPlacedV1.TYPE,1,order.id(),order.customerId(),order.items().size(),order.total(),order.placedAt());
        try { outbox.save(new OutboxEventEntity(event.eventId(),order.id(),event.eventType(),json.writeValueAsString(event),event.occurredAt())); }
        catch(JsonProcessingException ex){ throw new IllegalStateException("OrderPlaced serialization failed",ex); }
        return new PlacedOrder(order.id(),event.eventId(),order.placedAt());
    }
    public record PlacedOrder(UUID orderId, UUID eventId, java.time.Instant acceptedAt) {}
}
