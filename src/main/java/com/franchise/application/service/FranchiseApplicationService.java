package com.franchise.application.service;

import com.franchise.application.model.FranchiseCreateDto;
import com.franchise.application.model.FranchiseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseApplicationService {

  Flux<FranchiseDto> getFranchises();

  Mono<FranchiseDto> getFranchise(final String id);

  Mono<FranchiseDto> saveFranchise(final FranchiseCreateDto franchiseCreateDto);

  Mono<FranchiseDto> updateFranchise(final String id, final FranchiseCreateDto franchiseCreateDto);

  Mono<FranchiseDto> deleteFranchise(final String id);
}
