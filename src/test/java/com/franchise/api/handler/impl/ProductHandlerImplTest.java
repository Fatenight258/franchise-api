package com.franchise.api.handler.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franchise.api.exception.GlobalErrorHandler;
import com.franchise.api.router.ProductRouter;
import com.franchise.application.model.ProductDto;
import com.franchise.application.model.TopStockProductDto;
import com.franchise.application.service.ProductApplicationService;
import com.franchise.domain.exception.NotFoundException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ProductHandlerImplTest {

  private ProductApplicationService productApplicationService;
  private WebTestClient client;

  @BeforeEach
  void setUp() {
    productApplicationService = mock(ProductApplicationService.class);
    final ProductHandlerImpl handler = new ProductHandlerImpl(productApplicationService,
        Validation.buildDefaultValidatorFactory().getValidator());

    client = WebTestClient
        .bindToRouterFunction(new ProductRouter().productRoutes(handler))
        .handlerStrategies(HandlerStrategies.builder()
            .exceptionHandler(new GlobalErrorHandler(new ObjectMapper()))
            .build())
        .build();
  }

  @Test
  void createProductReturns201() {
    final ProductDto created = new ProductDto().id(19).branchId(1).branchName("El Poblado")
        .name("Jugo de Guanabana").stock(50);
    when(productApplicationService.saveProduct(eq(1), any())).thenReturn(Mono.just(created));

    client.post().uri("/branches/1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"Jugo de Guanabana\",\"stock\":50}")
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("$.id").isEqualTo(19)
        .jsonPath("$.branchName").isEqualTo("El Poblado");
  }

  @Test
  void createProductReturns400WhenStockIsNegative() {
    client.post().uri("/branches/1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"Jugo de Guanabana\",\"stock\":-1}")
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody().jsonPath("$.error").isEqualTo(400);

    verify(productApplicationService, never()).saveProduct(anyInt(), any());
  }

  @Test
  void createProductReturns400WhenIdIsNotANumber() {
    client.post().uri("/branches/abc/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"Jugo de Guanabana\",\"stock\":50}")
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody().jsonPath("$.message").isEqualTo("Invalid id: abc");
  }

  @Test
  void createProductReturns404WhenBranchDoesNotExist() {
    when(productApplicationService.saveProduct(eq(99), any()))
        .thenReturn(Mono.error(NotFoundException.branch(99)));

    client.post().uri("/branches/99/products")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"name\":\"Jugo de Guanabana\",\"stock\":50}")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody().jsonPath("$.message").isEqualTo("Branch not found: 99");
  }

  @Test
  void updateProductStockReturns200() {
    final ProductDto updated = new ProductDto().id(1).branchId(1).branchName("El Poblado")
        .name("Jugo de Mango").stock(80);
    when(productApplicationService.updateProductStock(eq(1), any())).thenReturn(Mono.just(updated));

    client.patch().uri("/products/1/stock")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue("{\"stock\":80}")
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$.stock").isEqualTo(80);
  }

  @Test
  void deleteProductReturns204() {
    when(productApplicationService.deleteProduct(1)).thenReturn(Mono.empty());

    client.delete().uri("/products/1")
        .exchange()
        .expectStatus().isNoContent()
        .expectBody().isEmpty();
  }

  @Test
  void deleteProductReturns404WhenProductDoesNotExist() {
    when(productApplicationService.deleteProduct(99)).thenReturn(Mono.error(NotFoundException.product(99)));

    client.delete().uri("/products/99")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void getTopStockProductsReturnsList() {
    when(productApplicationService.getTopStockProducts(1)).thenReturn(Flux.just(
        new TopStockProductDto().branchId(1).branchName("El Poblado").productId(1)
            .productName("Jugo de Mango").stock(120),
        new TopStockProductDto().branchId(2).branchName("Laureles").productId(5)
            .productName("Jugo de Mango").stock(60)));

    client.get().uri("/franchises/1/top-stock-products")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.length()").isEqualTo(2)
        .jsonPath("$[0].branchName").isEqualTo("El Poblado")
        .jsonPath("$[1].stock").isEqualTo(60);
  }
}
