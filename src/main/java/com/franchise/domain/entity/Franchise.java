package com.franchise.domain.entity;


import org.apache.commons.lang3.math.NumberUtils;

public class Franchise {

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_NAME = "name";
  public static final String PROPERTY_STATUS = "status";

  private final Integer id;
  private final String name;
  private final String status;

  public Franchise(Integer id, String name, String status) {
    this.id = id;
    this.name = name;
    this.status = status;
  }

  public Integer getId() {
    return id;
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
    private String name;
    private String status;

    public Builder id(Integer id) {
      this.id = id;
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

    public Franchise build() {
      return new Franchise(id, name, status);
    }
  }
}
