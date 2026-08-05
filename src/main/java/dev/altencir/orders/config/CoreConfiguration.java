package dev.altencir.orders.config;
import java.time.Clock; import java.util.Map;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder; import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
@Configuration public class CoreConfiguration {
 @Bean Clock utcClock(){return Clock.systemUTC();}
 @Bean EntityManagerFactoryBuilder entityManagerFactoryBuilder(){var vendor=new HibernateJpaVendorAdapter();vendor.setGenerateDdl(false);return new EntityManagerFactoryBuilder(vendor,dataSource->Map.of("hibernate.hbm2ddl.auto","validate","hibernate.jdbc.time_zone","UTC"),null);}
}
