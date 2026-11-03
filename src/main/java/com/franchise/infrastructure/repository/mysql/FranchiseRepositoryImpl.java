package com.franchise.infrastructure.repository.mysql;

import com.franchise.domain.entity.Franchise;
import com.franchise.domain.port.FranchiseRepository;
import com.franchise.infrastructure.adapter.FranchiseAdapter;
import com.franchise.infrastructure.repository.mysql.queries.franchiseQueries;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Repository
public class FranchiseRepositoryImpl implements FranchiseRepository {

  private final DatabaseClient client;

  public FranchiseRepositoryImpl(final DatabaseClient client) {
    this.client = client;
  }

  @Override
  public Flux<Franchise> findAll() {
    return client.sql(franchiseQueries.findAll())
        .map(FranchiseAdapter::adapt)
        .all();
  }

  @Override
  public Mono<Franchise> findById(final Integer id) {
    return client.sql(franchiseQueries.findById())
        .bind(Franchise.PROPERTY_ID, id)
        .map(FranchiseAdapter::adapt)
        .one();
  }

  @Override
  public Mono<Franchise> insert(final Franchise franchise) {
    return client.sql(franchiseQueries.insert())
        .bind(Franchise.PROPERTY_NAME, franchise.getName())
        .filter(statement -> statement.returnGeneratedValues(Franchise.PROPERTY_ID))
        .map(row -> Objects.requireNonNull(row.get(Franchise.PROPERTY_ID, Integer.class)))
        .one()
        .flatMap(this::findById);
  }

  @Override
  public Mono<Franchise> update(final Franchise franchise) {
    return client.sql(franchiseQueries.update())
        .bind(Franchise.PROPERTY_ID, franchise.getId())
        .bind(Franchise.PROPERTY_NAME, franchise.getName())
        .fetch()
        .rowsUpdated()
        .filter(rows -> rows > 0)
        .flatMap(rows -> findById(franchise.getId()));
  }

  @Override
  public Mono<Void> delete(final Integer id) {
    return client.sql(franchiseQueries.delete())
        .bind(Franchise.PROPERTY_ID, id)
        .fetch()
        .rowsUpdated()
        .then();
  }
}
