package com.franchise.application.service.impl;

import com.franchise.application.mapper.FranchiseMapper;
import com.franchise.application.model.FranchiseCreateDto;
import com.franchise.application.model.FranchiseDto;
import com.franchise.application.service.FranchiseApplicationService;
import com.franchise.domain.service.FranchiseService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseApplicationServiceImpl implements FranchiseApplicationService {

  private final FranchiseService franchiseService;

  public FranchiseApplicationServiceImpl(final FranchiseService franchiseService) {
    this.franchiseService = franchiseService;
  }

  @Override
  public Flux<FranchiseDto> getFranchises() {
    return franchiseService.findAll()
        .map(FranchiseMapper::toDto);
  }

  @Override
  public Mono<FranchiseDto> getFranchise(final String id) {
    return franchiseService.findById(id)
        .map(FranchiseMapper::toDto);
  }

  @Override
  public Mono<FranchiseDto> saveFranchise(final FranchiseCreateDto franchiseCreateDto) {
    return franchiseService.save(FranchiseMapper.toEntity(franchiseCreateDto))
        .map(FranchiseMapper::toDto);
  }

  @Override
  public Mono<FranchiseDto> updateFranchise(final String id, final FranchiseCreateDto franchiseCreateDto) {
    return franchiseService.findById(id)
        .flatMap(franchise -> franchiseService.update(
            FranchiseMapper.toEntity(franchise.getId(), franchiseCreateDto)))
        .map(FranchiseMapper::toDto);
  }

  @Override
  public Mono<FranchiseDto> deleteFranchise(final String id) {
    return franchiseService.deleteById(id)
        .map(FranchiseMapper::toDto);
  }
}
