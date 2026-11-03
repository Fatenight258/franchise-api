package com.franchise.domain.service.impl;

import com.franchise.domain.entity.Franchise;
import com.franchise.domain.exception.FranchiseNotFoundException;
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
  public Mono<Franchise> findById(final String id) {
    return parseId(id)
        .flatMap(repository::findById)
        .switchIfEmpty(Mono.error(new FranchiseNotFoundException(id)));
  }

  @Override
  public Mono<Franchise> save(final Franchise franchise) {
    return repository.insert(franchise);
  }

  @Override
  public Mono<Franchise> update(final Franchise franchise) {
    return repository.update(franchise)
        .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchise.getId())));
  }

  @Override
  public Mono<Franchise> deleteById(final String id) {
    return findById(id)
        .flatMap(franchise -> repository.delete(franchise.getId())
            .then(repository.findById(franchise.getId())));
  }

  private Mono<Integer> parseId(final String id) {
    return Mono.fromCallable(() -> Integer.valueOf(id))
        .onErrorMap(NumberFormatException.class,
            e -> new IllegalArgumentException("Invalid franchise id: " + id));
  }
}
