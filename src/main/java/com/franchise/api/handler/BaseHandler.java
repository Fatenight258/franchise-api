package com.franchise.api.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseHandler {

  String ID_PARAM_KEY = "id";

  default Integer pathId(final ServerRequest request) {
    final String id = request.pathVariable(ID_PARAM_KEY);
    try {
      return Integer.valueOf(id);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid id: " + id);
    }
  }

  default <T> Mono<ServerResponse> ok(final Mono<T> body) {
    return body.flatMap(dto -> ServerResponse.ok().bodyValue(dto));
  }

  default <T> Mono<ServerResponse> ok(final Flux<T> body, final Class<T> type) {
    return ServerResponse.ok().body(body, type);
  }

  default <T> Mono<ServerResponse> created(final Mono<T> body) {
    return body.flatMap(dto -> ServerResponse.status(HttpStatus.CREATED).bodyValue(dto));
  }

  default <T> Mono<T> validate(final Mono<T> body, final Validator validator) {
    return body
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Request body is required")))
        .flatMap(dto -> {
          final Set<ConstraintViolation<T>> violations = validator.validate(dto);
          return violations.isEmpty()
              ? Mono.just(dto)
              : Mono.error(new ConstraintViolationException(violations));
        });
  }
}
