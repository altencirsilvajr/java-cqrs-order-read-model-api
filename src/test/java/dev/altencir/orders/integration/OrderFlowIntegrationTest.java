package dev.altencir.orders.integration;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration; import java.util.Map;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.test.web.client.TestRestTemplate; import org.springframework.http.*; import org.springframework.test.context.DynamicPropertyRegistry; import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer; import org.testcontainers.junit.jupiter.Container; import org.testcontainers.junit.jupiter.Testcontainers; import org.testcontainers.kafka.ConfluentKafkaContainer; import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
class OrderFlowIntegrationTest {
 @Container static final PostgreSQLContainer<?> WRITE=new PostgreSQLContainer<>("postgres:17-alpine").withDatabaseName("orders_write").withUsername("orders").withPassword("orders");
 @Container static final PostgreSQLContainer<?> READ=new PostgreSQLContainer<>("postgres:17-alpine").withDatabaseName("orders_read").withUsername("orders").withPassword("orders");
 @Container static final ConfluentKafkaContainer KAFKA=new ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.7.1"));
 @DynamicPropertySource static void properties(DynamicPropertyRegistry r){r.add("app.datasource.write.url",WRITE::getJdbcUrl);r.add("app.datasource.write.username",WRITE::getUsername);r.add("app.datasource.write.password",WRITE::getPassword);r.add("app.datasource.read.url",READ::getJdbcUrl);r.add("app.datasource.read.username",READ::getUsername);r.add("app.datasource.read.password",READ::getPassword);r.add("spring.kafka.bootstrap-servers",KAFKA::getBootstrapServers);r.add("app.outbox.fixed-delay",()->"100");}
 @Autowired TestRestTemplate http;
 @Test void acceptedOrderEventuallyAppearsInReadModel() throws Exception {
  var request=Map.of("customerId","customer-42","items",java.util.List.of(Map.of("sku","JAVA-21","quantity",2,"unitPrice",19.95)));
  var accepted=http.postForEntity("/api/orders",request,Map.class);assertThat(accepted.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);var id=accepted.getBody().get("orderId");
  ResponseEntity<Map> read=null;var deadline=System.nanoTime()+Duration.ofSeconds(20).toNanos();do{Thread.sleep(200);read=http.getForEntity("/api/orders/"+id,Map.class);}while(read.getStatusCode()!=HttpStatus.OK&&System.nanoTime()<deadline);
  assertThat(read.getStatusCode()).isEqualTo(HttpStatus.OK);assertThat(read.getBody()).containsEntry("customerId","customer-42");
  var status=http.getForEntity("/api/orders/"+id+"/projection-status",Map.class);assertThat(status.getBody()).containsEntry("state","PROJECTED");
 }
}
