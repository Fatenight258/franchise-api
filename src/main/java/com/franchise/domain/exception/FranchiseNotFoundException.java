package com.franchise.domain.exception;

public class FranchiseNotFoundException extends RuntimeException {

  public FranchiseNotFoundException(final Object id) {
    super("Franchise not found: " + id);
  }
}
