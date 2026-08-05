package dev.altencir.orders.config;
import javax.sql.DataSource; import org.flywaydb.core.Flyway; import org.springframework.beans.factory.annotation.Qualifier; import org.springframework.context.annotation.*;
@Configuration public class DatabaseMigrationConfiguration {
 @Bean(initMethod="migrate") Flyway writeFlyway(@Qualifier("writeDataSource") DataSource d){return Flyway.configure().dataSource(d).locations("classpath:db/write").load();}
 @Bean(initMethod="migrate") Flyway readFlyway(@Qualifier("readDataSource") DataSource d){return Flyway.configure().dataSource(d).locations("classpath:db/read").load();}
}
