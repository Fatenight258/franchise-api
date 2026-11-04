package com.franchise.infrastructure.repository.mysql;

import com.franchise.domain.entity.Branch;
import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;
import com.franchise.domain.port.ProductRepository;
import com.franchise.infrastructure.adapter.ProductAdapter;
import com.franchise.infrastructure.adapter.TopStockProductAdapter;
import com.franchise.infrastructure.repository.mysql.queries.productQueries;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final DatabaseClient client;

  public ProductRepositoryImpl(final DatabaseClient client) {
    this.client = client;
  }

  @Override
  public Mono<Product> findById(final Integer id) {
    return client.sql(productQueries.findById())
        .bind(Product.PROPERTY_ID, id)
        .map(ProductAdapter::adapt)
        .one();
  }

  @Override
  public Mono<Product> insert(final Product product) {
    return client.sql(productQueries.insert())
        .bind(Product.PROPERTY_BRANCH_ID, product.getBranchId())
        .bind(Product.PROPERTY_NAME, product.getName())
        .bind(Product.PROPERTY_STOCK, product.getStock())
        .filter(statement -> statement.returnGeneratedValues(Product.PROPERTY_ID))
        .map(row -> Objects.requireNonNull(row.get(Product.PROPERTY_ID, Integer.class)))
        .one()
        .flatMap(this::findById);
  }

  @Override
  public Mono<Product> update(final Product product) {
    return client.sql(productQueries.update())
        .bind(Product.PROPERTY_ID, product.getId())
        .bind(Product.PROPERTY_NAME, product.getName())
        .fetch()
        .rowsUpdated()
        .filter(rows -> rows > 0)
        .flatMap(rows -> findById(product.getId()));
  }

  @Override
  public Mono<Product> updateStock(final Product product) {
    return client.sql(productQueries.updateStock())
        .bind(Product.PROPERTY_ID, product.getId())
        .bind(Product.PROPERTY_STOCK, product.getStock())
        .fetch()
        .rowsUpdated()
        .filter(rows -> rows > 0)
        .flatMap(rows -> findById(product.getId()));
  }

  @Override
  public Mono<Void> delete(final Integer id) {
    return client.sql(productQueries.delete())
        .bind(Product.PROPERTY_ID, id)
        .fetch()
        .rowsUpdated()
        .then();
  }

  @Override
  public Flux<TopStockProduct> findTopStockByFranchiseId(final Integer franchiseId) {
    return client.sql(productQueries.findTopStockByFranchiseId())
        .bind(Branch.PROPERTY_FRANCHISE_ID, franchiseId)
        .map(TopStockProductAdapter::adapt)
        .all();
  }
}
