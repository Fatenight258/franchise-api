package com.franchise.domain.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.port.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productService;

  private final Product product = Product.builder()
      .id(1)
      .branchId(1)
      .branchName("El Poblado")
      .name("Jugo de Mango")
      .stock(120)
      .build();

  @Test
  void findByIdReturnsProductWhenExists() {
    when(productRepository.findById(1)).thenReturn(Mono.just(product));

    StepVerifier.create(productService.findById(1))
        .expectNext(product)
        .verifyComplete();
  }

  @Test
  void findByIdFailsWithNotFoundWhenMissing() {
    when(productRepository.findById(99)).thenReturn(Mono.empty());

    StepVerifier.create(productService.findById(99))
        .expectErrorSatisfies(error -> {
          assertThat(error).isInstanceOf(NotFoundException.class);
          assertThat(error).hasMessage("Product not found: 99");
        })
        .verify();
  }

  @Test
  void updateStockFailsWithNotFoundWhenNoRowsUpdated() {
    when(productRepository.updateStock(any(Product.class))).thenReturn(Mono.empty());

    StepVerifier.create(productService.updateStock(product))
        .expectError(NotFoundException.class)
        .verify();
  }

  @Test
  void deleteByIdDeletesProductWhenExists() {
    when(productRepository.findById(1)).thenReturn(Mono.just(product));
    when(productRepository.delete(1)).thenReturn(Mono.empty());

    StepVerifier.create(productService.deleteById(1))
        .verifyComplete();

    verify(productRepository).delete(1);
  }

  @Test
  void deleteByIdFailsWithNotFoundWhenMissing() {
    when(productRepository.findById(99)).thenReturn(Mono.empty());

    StepVerifier.create(productService.deleteById(99))
        .expectError(NotFoundException.class)
        .verify();

    verify(productRepository, never()).delete(any());
  }

  @Test
  void findTopStockByFranchiseIdReturnsAllTiedProducts() {
    final TopStockProduct mango = TopStockProduct.builder()
        .branchId(2).branchName("Laureles")
        .productId(5).productName("Jugo de Mango").stock(60)
        .build();
    final TopStockProduct banano = TopStockProduct.builder()
        .branchId(2).branchName("Laureles")
        .productId(6).productName("Batido de Banano").stock(60)
        .build();
    when(productRepository.findTopStockByFranchiseId(1)).thenReturn(Flux.just(mango, banano));

    StepVerifier.create(productService.findTopStockByFranchiseId(1))
        .expectNext(mango, banano)
        .verifyComplete();
  }
}
