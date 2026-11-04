package com.franchise.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BranchHandler extends BaseHandler {

  String FRANCHISE_BRANCHES_PATH = "/franchises/{id}/branches";
  String BRANCH_BY_ID_PATH = "/branches/{id}";

  Mono<ServerResponse> createBranch(final ServerRequest request);

  Mono<ServerResponse> updateBranch(final ServerRequest request);
}
