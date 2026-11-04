package com.franchise.domain.service;

import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> findById(Integer id);

  Mono<Product> save(Product product);

  Mono<Product> update(Product product);

  Mono<Product> updateStock(Product product);

  Mono<Void> deleteById(Integer id);

  Flux<TopStockProduct> findTopStockByFranchiseId(Integer franchiseId);
}
