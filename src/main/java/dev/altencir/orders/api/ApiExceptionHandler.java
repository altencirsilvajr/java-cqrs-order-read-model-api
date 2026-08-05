package dev.altencir.orders.api;
import java.net.URI;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*;
@RestControllerAdvice public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ProblemDetail> invalid(MethodArgumentNotValidException ex){var p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Request validation failed");p.setType(URI.create("urn:problem:validation"));p.setProperty("errors",ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).toList());return ResponseEntity.badRequest().body(p);}
    @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ProblemDetail> invalidDomain(IllegalArgumentException ex){var p=ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());p.setType(URI.create("urn:problem:invalid-order"));return ResponseEntity.badRequest().body(p);}
    @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ProblemDetail> notFound(ResourceNotFoundException ex){var p=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());p.setType(URI.create("urn:problem:resource-not-found"));return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p);}
}
