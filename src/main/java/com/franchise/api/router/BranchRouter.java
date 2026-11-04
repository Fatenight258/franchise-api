package com.franchise.api.router;

import static com.franchise.api.handler.BranchHandler.BRANCH_BY_ID_PATH;
import static com.franchise.api.handler.BranchHandler.FRANCHISE_BRANCHES_PATH;

import com.franchise.api.handler.BranchHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BranchRouter {

  @Bean
  public RouterFunction<ServerResponse> branchRoutes(final BranchHandler handler) {
    return RouterFunctions.route()
        .POST(FRANCHISE_BRANCHES_PATH, handler::createBranch)
        .PUT(BRANCH_BY_ID_PATH, handler::updateBranch)
        .build();
  }
}
