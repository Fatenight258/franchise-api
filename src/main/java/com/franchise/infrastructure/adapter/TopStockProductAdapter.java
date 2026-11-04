package com.franchise.infrastructure.adapter;

import com.franchise.domain.entity.TopStockProduct;
import io.r2dbc.spi.Readable;

public class TopStockProductAdapter {

  public static TopStockProduct adapt(final Readable row) {
    return TopStockProduct.builder()
        .branchId(row.get(TopStockProduct.PROPERTY_BRANCH_ID, Integer.class))
        .branchName(row.get(TopStockProduct.PROPERTY_BRANCH_NAME, String.class))
        .productId(row.get(TopStockProduct.PROPERTY_PRODUCT_ID, Integer.class))
        .productName(row.get(TopStockProduct.PROPERTY_PRODUCT_NAME, String.class))
        .stock(row.get(TopStockProduct.PROPERTY_STOCK, Integer.class))
        .build();
  }
}
