package com.franchise.domain.service;

import com.franchise.domain.entity.Branch;
import reactor.core.publisher.Mono;

public interface BranchService {

  Mono<Branch> findById(Integer id);

  Mono<Branch> save(Branch branch);

  Mono<Branch> update(Branch branch);
}
