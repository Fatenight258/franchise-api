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
 * Request body to update the stock of a product
 */

@Schema(name = "ProductStockUpdateDto", description = "Request body to update the stock of a product")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class ProductStockUpdateDto {

  private Integer stock;

  public ProductStockUpdateDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductStockUpdateDto(Integer stock) {
    this.stock = stock;
  }

  public ProductStockUpdateDto stock(Integer stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Get stock
   * minimum: 0
   * @return stock
   */
  @NotNull @Min(0) 
  @Schema(name = "stock", example = "80", requiredMode = Schema.RequiredMode.REQUIRED)
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
    ProductStockUpdateDto productStockUpdateDto = (ProductStockUpdateDto) o;
    return Objects.equals(this.stock, productStockUpdateDto.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductStockUpdateDto {\n");
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

