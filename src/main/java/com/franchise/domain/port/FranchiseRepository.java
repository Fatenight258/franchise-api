package com.franchise.domain.port;

import com.franchise.domain.entity.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {

  Flux<Franchise> findAll();

  Mono<Franchise> findById(final Integer id);

  Mono<Franchise> insert(final Franchise franchise);

  Mono<Franchise> update(final Franchise franchise);

  Mono<Void> delete(final Integer id);
}
