package dev.altencir.orders.api;

import dev.altencir.orders.domain.OrderItem;
import dev.altencir.orders.write.PlaceOrderService;
import jakarta.validation.Valid; import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.net.URI; import java.time.Instant; import java.util.List; import java.util.UUID;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/orders")
public class OrderController {
    private final PlaceOrderService service;
    public OrderController(PlaceOrderService service){this.service=service;}
    @PostMapping public ResponseEntity<AcceptedOrder> place(@Valid @RequestBody PlaceOrderRequest request){
        var result=service.place(request.customerId(),request.items().stream().map(i->new OrderItem(i.sku(),i.quantity(),i.unitPrice())).toList());
        var location=URI.create("/api/orders/"+result.orderId()+"/projection-status");
        return ResponseEntity.accepted().location(location).body(new AcceptedOrder(result.orderId(),result.eventId(),"PENDING",result.acceptedAt(),location.toString()));
    }
    public record PlaceOrderRequest(@NotBlank String customerId,@NotEmpty List<@Valid ItemRequest> items){}
    public record ItemRequest(@NotBlank String sku,@Min(1) int quantity,@NotNull @DecimalMin("0.00") BigDecimal unitPrice){}
    public record AcceptedOrder(UUID orderId,UUID eventId,String projectionState,Instant acceptedAt,String statusUrl){}
}
