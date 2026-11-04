package com.franchise.infrastructure.repository.mysql;

import com.franchise.domain.entity.Branch;
import com.franchise.domain.port.BranchRepository;
import com.franchise.infrastructure.adapter.BranchAdapter;
import com.franchise.infrastructure.repository.mysql.queries.branchQueries;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Repository
public class BranchRepositoryImpl implements BranchRepository {

  private final DatabaseClient client;

  public BranchRepositoryImpl(final DatabaseClient client) {
    this.client = client;
  }

  @Override
  public Mono<Branch> findById(final Integer id) {
    return client.sql(branchQueries.findById())
        .bind(Branch.PROPERTY_ID, id)
        .map(BranchAdapter::adapt)
        .one();
  }

  @Override
  public Mono<Branch> insert(final Branch branch) {
    return client.sql(branchQueries.insert())
        .bind(Branch.PROPERTY_FRANCHISE_ID, branch.getFranchiseId())
        .bind(Branch.PROPERTY_NAME, branch.getName())
        .filter(statement -> statement.returnGeneratedValues(Branch.PROPERTY_ID))
        .map(row -> Objects.requireNonNull(row.get(Branch.PROPERTY_ID, Integer.class)))
        .one()
        .flatMap(this::findById);
  }

  @Override
  public Mono<Branch> update(final Branch branch) {
    return client.sql(branchQueries.update())
        .bind(Branch.PROPERTY_ID, branch.getId())
        .bind(Branch.PROPERTY_NAME, branch.getName())
        .fetch()
        .rowsUpdated()
        .filter(rows -> rows > 0)
        .flatMap(rows -> findById(branch.getId()));
  }
}
