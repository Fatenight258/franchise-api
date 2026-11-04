package com.franchise.domain.entity;

public class TopStockProduct {

  public static final String PROPERTY_BRANCH_ID = "branch_id";
  public static final String PROPERTY_BRANCH_NAME = "branch_name";
  public static final String PROPERTY_PRODUCT_ID = "product_id";
  public static final String PROPERTY_PRODUCT_NAME = "product_name";
  public static final String PROPERTY_STOCK = "stock";

  private final Integer branchId;
  private final String branchName;
  private final Integer productId;
  private final String productName;
  private final Integer stock;

  public TopStockProduct(Integer branchId, String branchName, Integer productId, String productName,
      Integer stock) {
    this.branchId = branchId;
    this.branchName = branchName;
    this.productId = productId;
    this.productName = productName;
    this.stock = stock;
  }

  public Integer getBranchId() {
    return branchId;
  }
  public String getBranchName() {
    return branchName;
  }
  public Integer getProductId() {
    return productId;
  }
  public String getProductName() {
    return productName;
  }
  public Integer getStock() {
    return stock;
  }

  public static Builder builder() {
    return new Builder();
  }
  public static final class Builder {
    private Integer branchId;
    private String branchName;
    private Integer productId;
    private String productName;
    private Integer stock;

    public Builder branchId(Integer branchId) {
      this.branchId = branchId;
      return this;
    }

    public Builder branchName(String branchName) {
      this.branchName = branchName;
      return this;
    }

    public Builder productId(Integer productId) {
      this.productId = productId;
      return this;
    }

    public Builder productName(String productName) {
      this.productName = productName;
      return this;
    }

    public Builder stock(Integer stock) {
      this.stock = stock;
      return this;
    }

    public TopStockProduct build() {
      return new TopStockProduct(branchId, branchName, productId, productName, stock);
    }
  }
}
