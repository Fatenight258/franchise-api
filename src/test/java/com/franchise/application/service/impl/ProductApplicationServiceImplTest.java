package com.franchise.application.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.franchise.application.model.ProductCreateDto;
import com.franchise.application.model.ProductStockUpdateDto;
import com.franchise.domain.entity.Branch;
import com.franchise.domain.entity.Franchise;
import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;
import com.franchise.domain.exception.NotFoundException;
import com.franchise.domain.service.BranchService;
import com.franchise.domain.service.FranchiseService;
import com.franchise.domain.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductApplicationServiceImplTest {

  @Mock
  private ProductService productService;

  @Mock
  private BranchService branchService;

  @Mock
  private FranchiseService franchiseService;

  @InjectMocks
  private ProductApplicationServiceImpl productApplicationService;

  private final Branch branch = Branch.builder()
      .id(1).franchiseId(1).franchiseName("Cosechas").name("El Poblado").status("ACTIVE")
      .build();

  private final Product product = Product.builder()
      .id(1).branchId(1).branchName("El Poblado").name("Jugo de Mango").stock(120)
      .build();

  @Test
  void saveProductCreatesProductForExistingBranch() {
    when(branchService.findById(1)).thenReturn(Mono.just(branch));
    when(productService.save(any(Product.class))).thenReturn(Mono.just(product));

    StepVerifier.create(productApplicationService.saveProduct(1, new ProductCreateDto("Jugo de Mango", 120)))
        .assertNext(productDto -> {
          assertThat(productDto.getId()).isEqualTo(1);
          assertThat(productDto.getBranchName()).isEqualTo("El Poblado");
          assertThat(productDto.getStock()).isEqualTo(120);
        })
        .verifyComplete();
  }

  @Test
  void saveProductFailsWhenBranchDoesNotExist() {
    when(branchService.findById(99)).thenReturn(Mono.error(NotFoundException.branch(99)));

    StepVerifier.create(productApplicationService.saveProduct(99, new ProductCreateDto("Jugo de Mango", 10)))
        .expectError(NotFoundException.class)
        .verify();

    verify(productService, never()).save(any());
  }

  @Test
  void updateProductStockSendsOnlyTheNewStock() {
    final Product updated = Product.builder()
        .id(1).branchId(1).branchName("El Poblado").name("Jugo de Mango").stock(80)
        .build();
    when(productService.findById(1)).thenReturn(Mono.just(product));
    when(productService.updateStock(any(Product.class))).thenReturn(Mono.just(updated));

    StepVerifier.create(productApplicationService.updateProductStock(1, new ProductStockUpdateDto(80)))
        .assertNext(productDto -> assertThat(productDto.getStock()).isEqualTo(80))
        .verifyComplete();

    final ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
    verify(productService).updateStock(captor.capture());
    assertThat(captor.getValue().getId()).isEqualTo(1);
    assertThat(captor.getValue().getStock()).isEqualTo(80);
  }

  @Test
  void getTopStockProductsMapsEveryProduct() {
    final Franchise franchise = Franchise.builder().id(1).name("Cosechas").status("ACTIVE").build();
    final TopStockProduct mango = TopStockProduct.builder()
        .branchId(2).branchName("Laureles").productId(5).productName("Jugo de Mango").stock(60)
        .build();
    final TopStockProduct banano = TopStockProduct.builder()
        .branchId(2).branchName("Laureles").productId(6).productName("Batido de Banano").stock(60)
        .build();
    when(franchiseService.findById(1)).thenReturn(Mono.just(franchise));
    when(productService.findTopStockByFranchiseId(1)).thenReturn(Flux.just(mango, banano));

    StepVerifier.create(productApplicationService.getTopStockProducts(1))
        .assertNext(dto -> assertThat(dto.getProductName()).isEqualTo("Jugo de Mango"))
        .assertNext(dto -> assertThat(dto.getProductName()).isEqualTo("Batido de Banano"))
        .verifyComplete();
  }

  @Test
  void getTopStockProductsFailsWhenFranchiseDoesNotExist() {
    when(franchiseService.findById(99)).thenReturn(Mono.error(NotFoundException.franchise(99)));

    StepVerifier.create(productApplicationService.getTopStockProducts(99))
        .expectError(NotFoundException.class)
        .verify();

    verify(productService, never()).findTopStockByFranchiseId(anyInt());
  }
}
