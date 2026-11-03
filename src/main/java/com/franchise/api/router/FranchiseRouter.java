package com.franchise.api.router;

import static com.franchise.api.handler.FranchiseHandler.FRANCHISES_PATH;
import static com.franchise.api.handler.FranchiseHandler.FRANCHISE_BY_ID_PATH;

import com.franchise.api.handler.FranchiseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranchiseRouter {

  @Bean
  public RouterFunction<ServerResponse> franchiseRoutes(final FranchiseHandler handler) {
    return RouterFunctions.route()
        .GET(FRANCHISES_PATH, handler::getFranchises)
        .POST(FRANCHISES_PATH, handler::createFranchise)
        .GET(FRANCHISE_BY_ID_PATH, handler::getFranchiseById)
        .PUT(FRANCHISE_BY_ID_PATH, handler::updateFranchise)
        .DELETE(FRANCHISE_BY_ID_PATH, handler::deleteFranchise)
        .build();
  }
}
