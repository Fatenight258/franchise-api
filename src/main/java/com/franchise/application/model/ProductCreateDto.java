package com.franchise.application.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Request body to create or update a product
 */

@Schema(name = "ProductCreateDto", description = "Request body to create or update a product")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class ProductCreateDto {

  private String name;

  private Integer stock;

  public ProductCreateDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductCreateDto(String name, Integer stock) {
    this.name = name;
    this.stock = stock;
  }

  public ProductCreateDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull @Size(min = 1, max = 100) 
  @Schema(name = "name", example = "Jugo de Mango", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductCreateDto stock(Integer stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Get stock
   * minimum: 0
   * @return stock
   */
  @NotNull @Min(0) 
  @Schema(name = "stock", example = "120", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stock")
  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductCreateDto productCreateDto = (ProductCreateDto) o;
    return Objects.equals(this.name, productCreateDto.name) &&
        Objects.equals(this.stock, productCreateDto.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, stock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductCreateDto {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    stock: ").append(toIndentedString(stock)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

