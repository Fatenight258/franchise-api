package com.franchise.application.service.impl;

import com.franchise.application.mapper.BranchMapper;
import com.franchise.application.model.BranchCreateDto;
import com.franchise.application.model.BranchDto;
import com.franchise.application.service.BranchApplicationService;
import com.franchise.domain.service.BranchService;
import com.franchise.domain.service.FranchiseService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BranchApplicationServiceImpl implements BranchApplicationService {

  private final BranchService branchService;
  private final FranchiseService franchiseService;

  public BranchApplicationServiceImpl(final BranchService branchService,
      final FranchiseService franchiseService) {
    this.branchService = branchService;
    this.franchiseService = franchiseService;
  }

  @Override
  public Mono<BranchDto> saveBranch(final Integer franchiseId, final BranchCreateDto branchCreateDto) {
    return franchiseService.findById(franchiseId)
        .flatMap(franchise -> branchService.save(
            BranchMapper.toCreateEntity(franchise.getId(), branchCreateDto)))
        .map(BranchMapper::toDto);
  }

  @Override
  public Mono<BranchDto> updateBranch(final Integer id, final BranchCreateDto branchCreateDto) {
    return branchService.findById(id)
        .flatMap(branch -> branchService.update(
            BranchMapper.toUpdateEntity(branch.getId(), branchCreateDto)))
        .map(BranchMapper::toDto);
  }
}
