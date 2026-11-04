package com.franchise.domain.exception;

public class BranchNotFoundException extends RuntimeException {

  public BranchNotFoundException(final Object id) {
    super("Branch not found: " + id);
  }
}
