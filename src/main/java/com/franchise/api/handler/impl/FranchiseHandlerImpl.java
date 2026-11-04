package com.franchise.api.handler.impl;

import com.franchise.api.handler.FranchiseHandler;
import com.franchise.application.model.FranchiseCreateDto;
import com.franchise.application.model.FranchiseDto;
import com.franchise.application.service.FranchiseApplicationService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FranchiseHandlerImpl implements FranchiseHandler {

  private final FranchiseApplicationService franchiseApplicationService;
  private final Validator validator;

  public FranchiseHandlerImpl(final FranchiseApplicationService franchiseApplicationService,
      final Validator validator) {
    this.franchiseApplicationService = franchiseApplicationService;
    this.validator = validator;
  }

  @Override
  public Mono<ServerResponse> getFranchises(final ServerRequest request) {
    return ok(franchiseApplicationService.getFranchises(), FranchiseDto.class);
  }

  @Override
  public Mono<ServerResponse> getFranchiseById(final ServerRequest request) {
    final Integer id = pathId(request);
    return ok(franchiseApplicationService.getFranchise(id));
  }

  @Override
  public Mono<ServerResponse> createFranchise(final ServerRequest request) {
    return created(validate(request.bodyToMono(FranchiseCreateDto.class), validator)
        .flatMap(franchiseApplicationService::saveFranchise));
  }

  @Override
  public Mono<ServerResponse> updateFranchise(final ServerRequest request) {
    final Integer id = pathId(request);
    return ok(validate(request.bodyToMono(FranchiseCreateDto.class), validator)
        .flatMap(franchiseCreateDto -> franchiseApplicationService.updateFranchise(id, franchiseCreateDto)));
  }

  @Override
  public Mono<ServerResponse> deleteFranchise(final ServerRequest request) {
    final Integer id = pathId(request);
    return ok(franchiseApplicationService.deleteFranchise(id));
  }
}
