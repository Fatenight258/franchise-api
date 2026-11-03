package com.franchise.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface FranchiseHandler extends BaseHandler {

  String FRANCHISES_PATH = "/franchises";
  String FRANCHISE_BY_ID_PATH = "/franchises/{id}";

  Mono<ServerResponse> getFranchises(final ServerRequest serverRequest);

  Mono<ServerResponse> getFranchiseById(final ServerRequest serverRequest);

  Mono<ServerResponse> createFranchise(final ServerRequest serverRequest);

  Mono<ServerResponse> updateFranchise(final ServerRequest serverRequest);

  Mono<ServerResponse> deleteFranchise(final ServerRequest serverRequest);
}
