package dev.altencir.orders.config;
import org.apache.kafka.clients.admin.NewTopic; import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*; import org.springframework.kafka.config.TopicBuilder;
@Configuration public class KafkaConfiguration { @Bean NewTopic orderPlacedTopic(@Value("${app.kafka.topic}") String name){return TopicBuilder.name(name).partitions(3).replicas(1).build();} }
