package com.franchise.domain.service.impl;

import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.port.ProductRepository;
import com.franchise.domain.service.ProductService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<Product> findById(final Integer id) {
    return productRepository.findById(id)
        .switchIfEmpty(Mono.error(NotFoundException.product(id)));
  }

  @Override
  public Mono<Product> save(final Product product) {
    return productRepository.insert(product);
  }

  @Override
  public Mono<Product> update(final Product product) {
    return productRepository.update(product)
        .switchIfEmpty(Mono.error(NotFoundException.product(product.getId())));
  }

  @Override
  public Mono<Product> updateStock(final Product product) {
    return productRepository.updateStock(product)
        .switchIfEmpty(Mono.error(NotFoundException.product(product.getId())));
  }

  @Override
  public Mono<Void> deleteById(final Integer id) {
    return findById(id)
        .flatMap(product -> productRepository.delete(product.getId()));
  }

  @Override
  public Flux<TopStockProduct> findTopStockByFranchiseId(final Integer franchiseId) {
    return productRepository.findTopStockByFranchiseId(franchiseId);
  }
}
