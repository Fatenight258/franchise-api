package com.franchise.api.handler.impl;

import com.franchise.api.handler.ProductHandler;
import com.franchise.application.model.ProductCreateDto;
import com.franchise.application.model.ProductStockUpdateDto;
import com.franchise.application.model.ProductUpdateDto;
import com.franchise.application.model.TopStockProductDto;
import com.franchise.application.service.ProductApplicationService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProductHandlerImpl implements ProductHandler {

  private final ProductApplicationService productApplicationService;
  private final Validator validator;

  public ProductHandlerImpl(final ProductApplicationService productApplicationService,
      final Validator validator) {
    this.productApplicationService = productApplicationService;
    this.validator = validator;
  }

  @Override
  public Mono<ServerResponse> createProduct(final ServerRequest request) {
    final Integer branchId = pathId(request);
    return created(validate(request.bodyToMono(ProductCreateDto.class), validator)
        .flatMap(productCreateDto -> productApplicationService.saveProduct(branchId, productCreateDto)));
  }

  @Override
  public Mono<ServerResponse> updateProduct(final ServerRequest request) {
    final Integer id = pathId(request);
    return ok(validate(request.bodyToMono(ProductUpdateDto.class), validator)
        .flatMap(productUpdateDto -> productApplicationService.updateProduct(id, productUpdateDto)));
  }

  @Override
  public Mono<ServerResponse> updateProductStock(final ServerRequest request) {
    final Integer id = pathId(request);
    return ok(validate(request.bodyToMono(ProductStockUpdateDto.class), validator)
        .flatMap(productStockUpdateDto ->
            productApplicationService.updateProductStock(id, productStockUpdateDto)));
  }

  @Override
  public Mono<ServerResponse> deleteProduct(final ServerRequest request) {
    final Integer id = pathId(request);
    return noContent(productApplicationService.deleteProduct(id));
  }

  @Override
  public Mono<ServerResponse> getTopStockProducts(final ServerRequest request) {
    final Integer franchiseId = pathId(request);
    return ok(productApplicationService.getTopStockProducts(franchiseId), TopStockProductDto.class);
  }
}
