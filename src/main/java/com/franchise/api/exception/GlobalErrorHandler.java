package com.franchise.api.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franchise.application.model.ErrorResponse;
import com.franchise.domain.exception.FranchiseNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorHandler implements WebExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

  private final ObjectMapper objectMapper;

  public GlobalErrorHandler(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Mono<Void> handle(final ServerWebExchange exchange, final Throwable ex) {
    final HttpStatusCode status = resolveStatus(ex);
    final ErrorResponse error = new ErrorResponse(status.value(), resolveMessage(ex, status))
        .debugMessage(ex.getClass().getSimpleName());

    if (status.is5xxServerError()) {
      LOGGER.error("Unhandled error on {}", exchange.getRequest().getPath(), ex);
    }
    return write(exchange.getResponse(), status, error);
  }

  private HttpStatusCode resolveStatus(final Throwable ex) {
    if (ex instanceof FranchiseNotFoundException) {
      return HttpStatus.NOT_FOUND;
    }
    if (ex instanceof IllegalArgumentException
        || ex instanceof ConstraintViolationException
        || ex instanceof ServerWebInputException) {
      return HttpStatus.BAD_REQUEST;
    }
    if (ex instanceof DataIntegrityViolationException) {
      return HttpStatus.CONFLICT;
    }
    if (ex instanceof ResponseStatusException responseStatusException) {
      return responseStatusException.getStatusCode();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

  private String resolveMessage(final Throwable ex, final HttpStatusCode status) {
    if (ex instanceof ConstraintViolationException violationException) {
      return violationException.getConstraintViolations().stream()
          .map(this::formatViolation)
          .sorted()
          .collect(Collectors.joining(", "));
    }
    if (ex instanceof ServerWebInputException) {
      return "Invalid request body";
    }
    if (ex instanceof DataIntegrityViolationException) {
      return "Resource already exists";
    }
    if (ex instanceof ResponseStatusException responseStatusException) {
      return responseStatusException.getReason() != null
          ? responseStatusException.getReason()
          : status.toString();
    }
    if (status.is5xxServerError()) {
      return "Internal server error";
    }
    return ex.getMessage();
  }

  private String formatViolation(final ConstraintViolation<?> violation) {
    return violation.getPropertyPath() + " " + violation.getMessage();
  }

  private Mono<Void> write(final ServerHttpResponse response, final HttpStatusCode status,
      final ErrorResponse error) {
    if (response.isCommitted()) {
      return Mono.empty();
    }
    response.setStatusCode(status);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    try {
      final DataBuffer buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(error));
      return response.writeWith(Mono.just(buffer));
    } catch (JsonProcessingException e) {
      final byte[] fallback = "{\"error\":500,\"message\":\"Internal server error\"}"
          .getBytes(StandardCharsets.UTF_8);
      return response.writeWith(Mono.just(response.bufferFactory().wrap(fallback)));
    }
  }
}
