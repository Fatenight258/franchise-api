package com.franchise.application.service.impl;

import com.franchise.application.mapper.ProductMapper;
import com.franchise.application.model.ProductCreateDto;
import com.franchise.application.model.ProductDto;
import com.franchise.application.model.ProductStockUpdateDto;
import com.franchise.application.model.ProductUpdateDto;
import com.franchise.application.model.TopStockProductDto;
import com.franchise.application.service.ProductApplicationService;
import com.franchise.domain.service.BranchService;
import com.franchise.domain.service.FranchiseService;
import com.franchise.domain.service.ProductService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {

  private final ProductService productService;
  private final BranchService branchService;
  private final FranchiseService franchiseService;

  public ProductApplicationServiceImpl(final ProductService productService,
      final BranchService branchService, final FranchiseService franchiseService) {
    this.productService = productService;
    this.branchService = branchService;
    this.franchiseService = franchiseService;
  }

  @Override
  public Mono<ProductDto> saveProduct(final Integer branchId, final ProductCreateDto productCreateDto) {
    return branchService.findById(branchId)
        .flatMap(branch -> productService.save(
            ProductMapper.toCreateEntity(branch.getId(), productCreateDto)))
        .map(ProductMapper::toDto);
  }

  @Override
  public Mono<ProductDto> updateProduct(final Integer id, final ProductUpdateDto productUpdateDto) {
    return productService.findById(id)
        .flatMap(product -> productService.update(
            ProductMapper.toUpdateEntity(product.getId(), productUpdateDto)))
        .map(ProductMapper::toDto);
  }

  @Override
  public Mono<ProductDto> updateProductStock(final Integer id,
      final ProductStockUpdateDto productStockUpdateDto) {
    return productService.findById(id)
        .flatMap(product -> productService.updateStock(
            ProductMapper.toUpdateStockEntity(product.getId(), productStockUpdateDto)))
        .map(ProductMapper::toDto);
  }

  @Override
  public Mono<Void> deleteProduct(final Integer id) {
    return productService.deleteById(id);
  }

  @Override
  public Flux<TopStockProductDto> getTopStockProducts(final Integer franchiseId) {
    return franchiseService.findById(franchiseId)
        .flatMapMany(franchise -> productService.findTopStockByFranchiseId(franchise.getId()))
        .map(ProductMapper::toTopStockDto);
  }
}
