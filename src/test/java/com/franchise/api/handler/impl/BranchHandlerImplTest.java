package com.franchise.api.handler.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franchise.api.exception.GlobalErrorHandler;
import com.franchise.api.router.BranchRouter;
import com.franchise.application.model.BranchDto;
import com.franchise.application.model.Status;
import com.franchise.application.service.BranchApplicationService;
import com.franchise.domain.exception.NotFoundException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import reactor.core.publisher.Mono;

class BranchHandlerImplTest {

  private BranchApplicationService branchApplicationService;
  private WebTestClient client;

  @BeforeEach
  void setUp() {
    branchApplicationService = mock(BranchApplicationService.class);
    final BranchHandlerImpl handler = new BranchHandlerImpl(branchApplicationService,
        Validation.buildDefaultValidatorFactory().getValidator());

    client = WebTestClient
        .bindToRouterFunction(new BranchRouter().branchRoutes(handler))
        .handlerStrategies(HandlerStrategies.builder()
            .exceptionHandler(new GlobalErrorHandler(new ObjectMapper()))
            .build())
        .build();
  }

  @Test
  void createBranchReturns201() {
    final BranchDto created = new BranchDto().id(8).franchiseId(1).franchiseName("Cosechas")
        .name("Envigado").status(Status.ACTIVE);
    when(branchApplicationService.saveBranch(eq(1), any())).thenReturn(Mono.just(created));

    client.post().uri("/franchises/1/branches")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"Envigado\"}")
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.franchiseName").isEqualTo("Cosechas")
        .jsonPath("$.status").isEqualTo("ACTIVE");
  }

  @Test
  void createBranchReturns400WhenBodyIsMissing() {
    client.post().uri("/franchises/1/branches")
        .contentType(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody().jsonPath("$.message").isEqualTo("Request body is required");
  }

  @Test
  void createBranchReturns409WhenNameAlreadyExists() {
    when(branchApplicationService.saveBranch(eq(1), any()))
        .thenReturn(Mono.error(new DuplicateKeyException("uk_branch_franchise_name")));

    client.post().uri("/franchises/1/branches")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"El Poblado\"}")
        .exchange()
        .expectStatus().isEqualTo(409)
        .expectBody().jsonPath("$.message").isEqualTo("Resource already exists");
  }

  @Test
  void updateBranchReturns404WhenBranchDoesNotExist() {
    when(branchApplicationService.updateBranch(eq(99), any()))
        .thenReturn(Mono.error(NotFoundException.branch(99)));

    client.put().uri("/branches/99")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"Envigado\"}")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody().jsonPath("$.message").isEqualTo("Branch not found: 99");
  }
}
