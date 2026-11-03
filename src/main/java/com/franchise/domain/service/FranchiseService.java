package com.franchise.domain.service;

import com.franchise.domain.entity.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseService {

  Flux<Franchise> findAll();

  Mono<Franchise> findById(String id);

  Mono<Franchise> save(Franchise franchise);

  Mono<Franchise> update(Franchise franchise);

  Mono<Franchise> deleteById(String id);
}
