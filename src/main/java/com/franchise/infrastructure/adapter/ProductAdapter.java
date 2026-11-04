package com.franchise.infrastructure.adapter;

import com.franchise.domain.entity.Product;
import io.r2dbc.spi.Readable;
import java.util.Objects;

public class ProductAdapter {

  public static Product adapt(final Readable row) {
    return Product.builder()
        .id(Objects.requireNonNull(row.get(Product.PROPERTY_ID, Integer.class)))
        .branchId(row.get(Product.PROPERTY_BRANCH_ID, Integer.class))
        .branchName(row.get(Product.PROPERTY_BRANCH_NAME, String.class))
        .name(row.get(Product.PROPERTY_NAME, String.class))
        .stock(row.get(Product.PROPERTY_STOCK, Integer.class))
        .build();
  }
}
