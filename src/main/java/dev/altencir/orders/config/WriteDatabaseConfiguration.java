package dev.altencir.orders.config;

import dev.altencir.orders.write.OrderEntity;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages="dev.altencir.orders.write",entityManagerFactoryRef="writeEntityManagerFactory",transactionManagerRef="writeTransactionManager")
public class WriteDatabaseConfiguration {
    @Bean @ConfigurationProperties("app.datasource.write") DataSourceProperties writeDataSourceProperties(){return new DataSourceProperties();}
    @Bean DataSource writeDataSource(@Qualifier("writeDataSourceProperties") DataSourceProperties p){return p.initializeDataSourceBuilder().build();}
    @Bean @DependsOn("writeFlyway") LocalContainerEntityManagerFactoryBean writeEntityManagerFactory(EntityManagerFactoryBuilder b,@Qualifier("writeDataSource") DataSource d){return b.dataSource(d).packages(OrderEntity.class).persistenceUnit("write").build();}
    @Bean PlatformTransactionManager writeTransactionManager(@Qualifier("writeEntityManagerFactory") LocalContainerEntityManagerFactoryBean f){return new JpaTransactionManager(f.getObject());}
}
