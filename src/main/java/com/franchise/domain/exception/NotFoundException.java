package com.franchise.domain.exception;

public class NotFoundException extends RuntimeException {

  private NotFoundException(final String message) {
    super(message);
  }

  public static NotFoundException franchise(final Object id) {
    return new NotFoundException("Franchise not found: " + id);
  }

  public static NotFoundException branch(final Object id) {
    return new NotFoundException("Branch not found: " + id);
  }

  public static NotFoundException product(final Object id) {
    return new NotFoundException("Product not found: " + id);
  }
}
