package com.example.hexagon.albummgt.user.core.domain;

public class DomainUserException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DomainUserException(String message) {
    super(message);
  }
}
