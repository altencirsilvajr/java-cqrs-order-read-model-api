package dev.altencir.orders.config;
import java.time.Clock;
import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
@Configuration public class CoreConfiguration { @Bean Clock utcClock(){return Clock.systemUTC();} }
