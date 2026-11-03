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
 * Product with the most stock in a branch, with the branch it belongs to
 */

@Schema(name = "TopStockProductDto", description = "Product with the most stock in a branch, with the branch it belongs to")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class TopStockProductDto {

  private @Nullable Integer branchId;

  private @Nullable String branchName;

  private @Nullable Integer productId;

  private @Nullable String productName;

  private @Nullable Integer stock;

  public TopStockProductDto branchId(@Nullable Integer branchId) {
    this.branchId = branchId;
    return this;
  }

  /**
   * Get branchId
   * @return branchId
   */
  
  @Schema(name = "branchId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branchId")
  public @Nullable Integer getBranchId() {
    return branchId;
  }

  public void setBranchId(@Nullable Integer branchId) {
    this.branchId = branchId;
  }

  public TopStockProductDto branchName(@Nullable String branchName) {
    this.branchName = branchName;
    return this;
  }

  /**
   * Get branchName
   * @return branchName
   */
  
  @Schema(name = "branchName", example = "El Poblado", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branchName")
  public @Nullable String getBranchName() {
    return branchName;
  }

  public void setBranchName(@Nullable String branchName) {
    this.branchName = branchName;
  }

  public TopStockProductDto productId(@Nullable Integer productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Get productId
   * @return productId
   */
  
  @Schema(name = "productId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("productId")
  public @Nullable Integer getProductId() {
    return productId;
  }

  public void setProductId(@Nullable Integer productId) {
    this.productId = productId;
  }

  public TopStockProductDto productName(@Nullable String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Get productName
   * @return productName
   */
  
  @Schema(name = "productName", example = "Jugo de Mango", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("productName")
  public @Nullable String getProductName() {
    return productName;
  }

  public void setProductName(@Nullable String productName) {
    this.productName = productName;
  }

  public TopStockProductDto stock(@Nullable Integer stock) {
    this.stock = stock;
    return this;
  }

  /**
   * Get stock
   * @return stock
   */
  
  @Schema(name = "stock", example = "120", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stock")
  public @Nullable Integer getStock() {
    return stock;
  }

  public void setStock(@Nullable Integer stock) {
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
    TopStockProductDto topStockProductDto = (TopStockProductDto) o;
    return Objects.equals(this.branchId, topStockProductDto.branchId) &&
        Objects.equals(this.branchName, topStockProductDto.branchName) &&
        Objects.equals(this.productId, topStockProductDto.productId) &&
        Objects.equals(this.productName, topStockProductDto.productName) &&
        Objects.equals(this.stock, topStockProductDto.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(branchId, branchName, productId, productName, stock);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TopStockProductDto {\n");
    sb.append("    branchId: ").append(toIndentedString(branchId)).append("\n");
    sb.append("    branchName: ").append(toIndentedString(branchName)).append("\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
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

