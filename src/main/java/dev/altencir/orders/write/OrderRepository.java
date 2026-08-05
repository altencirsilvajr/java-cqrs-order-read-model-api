package dev.altencir.orders.write;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {}
