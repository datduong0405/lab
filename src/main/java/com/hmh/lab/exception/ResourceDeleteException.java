package com.hmh.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceDeleteException extends RuntimeException {

  private final String message;

  public ResourceDeleteException(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
