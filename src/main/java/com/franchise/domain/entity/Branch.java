package com.franchise.domain.entity;

public class Branch {

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_FRANCHISE_ID = "franchise_id";
  public static final String PROPERTY_FRANCHISE_NAME = "franchise_name";
  public static final String PROPERTY_NAME = "name";
  public static final String PROPERTY_STATUS = "status";

  private final Integer id;
  private final Integer franchiseId;
  private final String franchiseName;
  private final String name;
  private final String status;

  public Branch(Integer id, Integer franchiseId, String franchiseName, String name, String status) {
    this.id = id;
    this.franchiseId = franchiseId;
    this.franchiseName = franchiseName;
    this.name = name;
    this.status = status;
  }

  public Integer getId() {
    return id;
  }
  public Integer getFranchiseId() {
    return franchiseId;
  }
  public String getFranchiseName() {
    return franchiseName;
  }
  public String getName() {
    return name;
  }
  public String getStatus() {
    return status;
  }

  public static Builder builder() {
    return new Builder();
  }
  public static final class Builder {
    private Integer id;
    private Integer franchiseId;
    private String franchiseName;
    private String name;
    private String status;

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder franchiseId(Integer franchiseId) {
      this.franchiseId = franchiseId;
      return this;
    }

    public Builder franchiseName(String franchiseName) {
      this.franchiseName = franchiseName;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Branch build() {
      return new Branch(id, franchiseId, franchiseName, name, status);
    }
  }
}
