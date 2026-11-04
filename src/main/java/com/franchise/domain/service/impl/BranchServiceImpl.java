package com.franchise.domain.service.impl;

import com.franchise.domain.entity.Branch;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.port.BranchRepository;
import com.franchise.domain.service.BranchService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BranchServiceImpl implements BranchService {

  private final BranchRepository branchRepository;

  public BranchServiceImpl(final BranchRepository branchRepository) {
    this.branchRepository = branchRepository;
  }

  @Override
  public Mono<Branch> findById(final Integer id) {
    return branchRepository.findById(id)
        .switchIfEmpty(Mono.error(NotFoundException.branch(id)));
  }

  @Override
  public Mono<Branch> save(final Branch branch) {
    return branchRepository.insert(branch);
  }

  @Override
  public Mono<Branch> update(final Branch branch) {
    return branchRepository.update(branch)
        .switchIfEmpty(Mono.error(NotFoundException.branch(branch.getId())));
  }
}
