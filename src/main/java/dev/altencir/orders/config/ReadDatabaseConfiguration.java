package dev.altencir.orders.config;

import dev.altencir.orders.read.OrderSummaryEntity;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier; import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties; import org.springframework.boot.context.properties.ConfigurationProperties; import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder; import org.springframework.context.annotation.*; import org.springframework.data.jpa.repository.config.EnableJpaRepositories; import org.springframework.orm.jpa.*; import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages="dev.altencir.orders.read",entityManagerFactoryRef="readEntityManagerFactory",transactionManagerRef="readTransactionManager")
public class ReadDatabaseConfiguration {
 @Bean @ConfigurationProperties("app.datasource.read") DataSourceProperties readDataSourceProperties(){return new DataSourceProperties();}
 @Bean DataSource readDataSource(@Qualifier("readDataSourceProperties") DataSourceProperties p){return p.initializeDataSourceBuilder().build();}
 @Bean @DependsOn("readFlyway") LocalContainerEntityManagerFactoryBean readEntityManagerFactory(EntityManagerFactoryBuilder b,@Qualifier("readDataSource") DataSource d){return b.dataSource(d).packages(OrderSummaryEntity.class).persistenceUnit("read").build();}
 @Bean PlatformTransactionManager readTransactionManager(@Qualifier("readEntityManagerFactory") LocalContainerEntityManagerFactoryBean f){return new JpaTransactionManager(f.getObject());}
}
