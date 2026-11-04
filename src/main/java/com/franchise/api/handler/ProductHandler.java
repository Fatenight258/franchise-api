package com.franchise.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ProductHandler extends BaseHandler {

  String BRANCH_PRODUCTS_PATH = "/branches/{id}/products";
  String PRODUCT_BY_ID_PATH = "/products/{id}";
  String PRODUCT_STOCK_PATH = "/products/{id}/stock";
  String FRANCHISE_TOP_STOCK_PRODUCTS_PATH = "/franchises/{id}/top-stock-products";

  Mono<ServerResponse> createProduct(final ServerRequest request);

  Mono<ServerResponse> updateProduct(final ServerRequest request);

  Mono<ServerResponse> updateProductStock(final ServerRequest request);

  Mono<ServerResponse> deleteProduct(final ServerRequest request);

  Mono<ServerResponse> getTopStockProducts(final ServerRequest request);
}
