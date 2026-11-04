package com.franchise.domain.service.impl;

import com.franchise.domain.entity.Franchise;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.port.FranchiseRepository;
import com.franchise.domain.service.FranchiseService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseServiceImpl implements FranchiseService {

  private final FranchiseRepository repository;

  public FranchiseServiceImpl(final FranchiseRepository repository) {
    this.repository = repository;
  }

  @Override
  public Flux<Franchise> findAll() {
    return repository.findAll();
  }

  @Override
  public Mono<Franchise> findById(final Integer id) {
    return repository.findById(id)
        .switchIfEmpty(Mono.error(NotFoundException.franchise(id)));
  }

  @Override
  public Mono<Franchise> save(final Franchise franchise) {
    return repository.insert(franchise);
  }

  @Override
  public Mono<Franchise> update(final Franchise franchise) {
    return repository.update(franchise)
        .switchIfEmpty(Mono.error(NotFoundException.franchise(franchise.getId())));
  }

  @Override
  public Mono<Franchise> deleteById(final Integer id) {
    return findById(id)
        .flatMap(franchise -> repository.delete(franchise.getId())
            .then(repository.findById(franchise.getId())));
  }
}
