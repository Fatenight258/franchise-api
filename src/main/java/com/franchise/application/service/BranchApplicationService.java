package com.franchise.application.service;

import com.franchise.application.model.BranchCreateDto;
import com.franchise.application.model.BranchDto;
import reactor.core.publisher.Mono;

public interface BranchApplicationService {

  Mono<BranchDto> saveBranch(final Integer franchiseId, final BranchCreateDto branchCreateDto);

  Mono<BranchDto> updateBranch(final Integer id, final BranchCreateDto branchCreateDto);
}
