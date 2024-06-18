package com.example.hexagon.albummgt.common.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
      super(message);
    }

  public NotFoundException(){

    }
}
