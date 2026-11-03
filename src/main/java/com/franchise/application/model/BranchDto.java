package com.franchise.application.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.franchise.application.model.Status;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Branch of a franchise
 */

@Schema(name = "BranchDto", description = "Branch of a franchise")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class BranchDto {

  private @Nullable Integer id;

  private @Nullable Integer franchiseId;

  private @Nullable String name;

  private @Nullable Status status;

  public BranchDto id(@Nullable Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public @Nullable Integer getId() {
    return id;
  }

  public void setId(@Nullable Integer id) {
    this.id = id;
  }

  public BranchDto franchiseId(@Nullable Integer franchiseId) {
    this.franchiseId = franchiseId;
    return this;
  }

  /**
   * Get franchiseId
   * @return franchiseId
   */
  
  @Schema(name = "franchiseId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("franchiseId")
  public @Nullable Integer getFranchiseId() {
    return franchiseId;
  }

  public void setFranchiseId(@Nullable Integer franchiseId) {
    this.franchiseId = franchiseId;
  }

  public BranchDto name(@Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  
  @Schema(name = "name", example = "El Poblado", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public @Nullable String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public BranchDto status(@Nullable Status status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public @Nullable Status getStatus() {
    return status;
  }

  public void setStatus(@Nullable Status status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BranchDto branchDto = (BranchDto) o;
    return Objects.equals(this.id, branchDto.id) &&
        Objects.equals(this.franchiseId, branchDto.franchiseId) &&
        Objects.equals(this.name, branchDto.name) &&
        Objects.equals(this.status, branchDto.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, franchiseId, name, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BranchDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    franchiseId: ").append(toIndentedString(franchiseId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

