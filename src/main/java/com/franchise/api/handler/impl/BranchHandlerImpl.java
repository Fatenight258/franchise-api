package com.franchise.api.handler.impl;

import com.franchise.api.handler.BranchHandler;
import com.franchise.application.model.BranchCreateDto;
import com.franchise.application.service.BranchApplicationService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BranchHandlerImpl implements BranchHandler {

  private final BranchApplicationService branchApplicationService;
  private final Validator validator;

  public BranchHandlerImpl(final BranchApplicationService branchApplicationService,
      final Validator validator) {
    this.branchApplicationService = branchApplicationService;
    this.validator = validator;
  }

  @Override
  public Mono<ServerResponse> createBranch(final ServerRequest request) {
    final Integer franchiseId = pathId(request);
    return created(validate(request.bodyToMono(BranchCreateDto.class), validator)
        .flatMap(branchCreateDto -> branchApplicationService.saveBranch(franchiseId, branchCreateDto)));
  }

  @Override
  public Mono<ServerResponse> updateBranch(final ServerRequest request) {
    final Integer id = pathId(request);
    return ok(validate(request.bodyToMono(BranchCreateDto.class), validator)
        .flatMap(branchCreateDto -> branchApplicationService.updateBranch(id, branchCreateDto)));
  }
}
