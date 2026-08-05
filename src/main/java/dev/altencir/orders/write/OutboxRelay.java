package dev.altencir.orders.write;

import io.micrometer.core.instrument.MeterRegistry; import java.time.Clock; import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value; import org.springframework.data.domain.PageRequest; import org.springframework.kafka.core.KafkaTemplate; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional;

@Component public class OutboxRelay {
 private final OutboxEventRepository outbox; private final KafkaTemplate<String,String> kafka; private final String topic; private final int batchSize; private final Clock clock; private final MeterRegistry metrics;
 public OutboxRelay(OutboxEventRepository outbox,KafkaTemplate<String,String> kafka,@Value("${app.kafka.topic}") String topic,@Value("${app.outbox.batch-size:50}") int batchSize,Clock clock,MeterRegistry metrics){this.outbox=outbox;this.kafka=kafka;this.topic=topic;this.batchSize=batchSize;this.clock=clock;this.metrics=metrics;}
 @Scheduled(fixedDelayString="${app.outbox.fixed-delay:250}") @Transactional("writeTransactionManager")
 public void publishPending(){for(var event:outbox.lockPending(PageRequest.of(0,batchSize))){try{kafka.send(topic,event.getAggregateId().toString(),event.getPayload()).get(10,TimeUnit.SECONDS);event.markPublished(clock.instant());metrics.counter("orders.outbox.published").increment();}catch(Exception ex){metrics.counter("orders.outbox.failures").increment();throw new IllegalStateException("Kafka acknowledgement failed",ex);}}}
}
