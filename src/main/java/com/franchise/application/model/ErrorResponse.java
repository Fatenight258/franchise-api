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
 * Standard error response
 */

@Schema(name = "ErrorResponse", description = "Standard error response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class ErrorResponse {

  private Integer error;

  private String message;

  private @Nullable String debugMessage;

  public ErrorResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ErrorResponse(Integer error, String message) {
    this.error = error;
    this.message = message;
  }

  public ErrorResponse error(Integer error) {
    this.error = error;
    return this;
  }

  /**
   * HTTP status code
   * @return error
   */
  @NotNull 
  @Schema(name = "error", example = "404", description = "HTTP status code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("error")
  public Integer getError() {
    return error;
  }

  public void setError(Integer error) {
    this.error = error;
  }

  public ErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  @NotNull 
  @Schema(name = "message", example = "Franchise not found: 99", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorResponse debugMessage(@Nullable String debugMessage) {
    this.debugMessage = debugMessage;
    return this;
  }

  /**
   * Get debugMessage
   * @return debugMessage
   */
  
  @Schema(name = "debugMessage", example = "", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("debugMessage")
  public @Nullable String getDebugMessage() {
    return debugMessage;
  }

  public void setDebugMessage(@Nullable String debugMessage) {
    this.debugMessage = debugMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.error, errorResponse.error) &&
        Objects.equals(this.message, errorResponse.message) &&
        Objects.equals(this.debugMessage, errorResponse.debugMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, message, debugMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    debugMessage: ").append(toIndentedString(debugMessage)).append("\n");
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

