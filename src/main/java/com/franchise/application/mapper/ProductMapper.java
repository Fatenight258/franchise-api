package com.franchise.application.mapper;

import com.franchise.application.model.ProductCreateDto;
import com.franchise.application.model.ProductDto;
import com.franchise.application.model.ProductStockUpdateDto;
import com.franchise.application.model.ProductUpdateDto;
import com.franchise.application.model.TopStockProductDto;
import com.franchise.domain.entity.Product;
import com.franchise.domain.entity.TopStockProduct;

public class ProductMapper {

  public static ProductDto toDto(final Product product) {
    return new ProductDto()
        .id(product.getId())
        .branchId(product.getBranchId())
        .branchName(product.getBranchName())
        .name(product.getName())
        .stock(product.getStock());
  }

  public static TopStockProductDto toTopStockDto(final TopStockProduct topStockProduct) {
    return new TopStockProductDto()
        .branchId(topStockProduct.getBranchId())
        .branchName(topStockProduct.getBranchName())
        .productId(topStockProduct.getProductId())
        .productName(topStockProduct.getProductName())
        .stock(topStockProduct.getStock());
  }

  public static Product toCreateEntity(final Integer branchId, final ProductCreateDto productCreateDto) {
    return Product.builder()
        .branchId(branchId)
        .name(productCreateDto.getName())
        .stock(productCreateDto.getStock())
        .build();
  }

  public static Product toUpdateEntity(final Integer id, final ProductUpdateDto productUpdateDto) {
    return Product.builder()
        .id(id)
        .name(productUpdateDto.getName())
        .build();
  }

  public static Product toUpdateStockEntity(final Integer id,
      final ProductStockUpdateDto productStockUpdateDto) {
    return Product.builder()
        .id(id)
        .stock(productStockUpdateDto.getStock())
        .build();
  }
}
