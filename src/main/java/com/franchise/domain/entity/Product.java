package com.franchise.domain.entity;

public class Product {

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_BRANCH_ID = "branch_id";
  public static final String PROPERTY_BRANCH_NAME = "branch_name";
  public static final String PROPERTY_NAME = "name";
  public static final String PROPERTY_STOCK = "stock";

  private final Integer id;
  private final Integer branchId;
  private final String branchName;
  private final String name;
  private final Integer stock;

  public Product(Integer id, Integer branchId, String branchName, String name, Integer stock) {
    this.id = id;
    this.branchId = branchId;
    this.branchName = branchName;
    this.name = name;
    this.stock = stock;
  }

  public Integer getId() {
    return id;
  }
  public Integer getBranchId() {
    return branchId;
  }
  public String getBranchName() {
    return branchName;
  }
  public String getName() {
    return name;
  }
  public Integer getStock() {
    return stock;
  }

  public static Builder builder() {
    return new Builder();
  }
  public static final class Builder {
    private Integer id;
    private Integer branchId;
    private String branchName;
    private String name;
    private Integer stock;

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder branchId(Integer branchId) {
      this.branchId = branchId;
      return this;
    }

    public Builder branchName(String branchName) {
      this.branchName = branchName;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder stock(Integer stock) {
      this.stock = stock;
      return this;
    }

    public Product build() {
      return new Product(id, branchId, branchName, name, stock);
    }
  }
}
