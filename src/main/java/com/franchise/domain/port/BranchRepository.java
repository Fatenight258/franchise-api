package com.franchise.domain.port;

import com.franchise.domain.entity.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepository {

  Mono<Branch> findById(final Integer id);

  Mono<Branch> insert(final Branch branch);

  Mono<Branch> update(final Branch branch);
}
