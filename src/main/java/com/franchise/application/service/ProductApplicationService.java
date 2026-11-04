package com.franchise.application.service;

import com.franchise.application.model.ProductCreateDto;
import com.franchise.application.model.ProductDto;
import com.franchise.application.model.ProductStockUpdateDto;
import com.franchise.application.model.ProductUpdateDto;
import com.franchise.application.model.TopStockProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductApplicationService {

  Mono<ProductDto> saveProduct(final Integer branchId, final ProductCreateDto productCreateDto);

  Mono<ProductDto> updateProduct(final Integer id, final ProductUpdateDto productUpdateDto);

  Mono<ProductDto> updateProductStock(final Integer id, final ProductStockUpdateDto productStockUpdateDto);

  Mono<Void> deleteProduct(final Integer id);

  Flux<TopStockProductDto> getTopStockProducts(final Integer franchiseId);
}
