package com.franchise.api.router;

import static com.franchise.api.handler.ProductHandler.BRANCH_PRODUCTS_PATH;
import static com.franchise.api.handler.ProductHandler.FRANCHISE_TOP_STOCK_PRODUCTS_PATH;
import static com.franchise.api.handler.ProductHandler.PRODUCT_BY_ID_PATH;
import static com.franchise.api.handler.ProductHandler.PRODUCT_STOCK_PATH;

import com.franchise.api.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

  @Bean
  public RouterFunction<ServerResponse> productRoutes(final ProductHandler handler) {
    return RouterFunctions.route()
        .POST(BRANCH_PRODUCTS_PATH, handler::createProduct)
        .PUT(PRODUCT_BY_ID_PATH, handler::updateProduct)
        .DELETE(PRODUCT_BY_ID_PATH, handler::deleteProduct)
        .PATCH(PRODUCT_STOCK_PATH, handler::updateProductStock)
        .GET(FRANCHISE_TOP_STOCK_PRODUCTS_PATH, handler::getTopStockProducts)
        .build();
  }
}
