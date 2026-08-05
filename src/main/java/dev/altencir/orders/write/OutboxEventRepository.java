package dev.altencir.orders.write;
import java.util.List; import java.util.UUID;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param;
public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select e from OutboxEventEntity e where e.publishedAt is null order by e.occurredAt") List<OutboxEventEntity> lockPending(Pageable pageable);
 long countByPublishedAtIsNull();
 long countByPublishedAtIsNotNull();
}
