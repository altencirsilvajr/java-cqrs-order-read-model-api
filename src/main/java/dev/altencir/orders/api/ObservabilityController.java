package dev.altencir.orders.api;
import dev.altencir.orders.application.ObservabilityService; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/observability") public class ObservabilityController {
 private final ObservabilityService service;
 public ObservabilityController(ObservabilityService service){this.service=service;}
 @GetMapping("/overview") ObservabilityService.Overview overview(){return service.overview();}
}
