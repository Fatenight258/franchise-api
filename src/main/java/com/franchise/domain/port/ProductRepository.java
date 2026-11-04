package com.franchise.domain.port;

import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

  Mono<Product> findById(final Integer id);

  Mono<Product> insert(final Product product);

  Mono<Product> update(final Product product);

  Mono<Product> updateStock(final Product product);

  Mono<Void> delete(final Integer id);

  Flux<TopStockProduct> findTopStockByFranchiseId(final Integer franchiseId);
}
