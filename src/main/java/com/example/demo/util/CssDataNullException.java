package com.example.demo.util;

public class CssDataNullException extends Exception {
  public CssDataNullException() {
    super("unable to get data,Something went wrong with css-Query");
  }

  public CssDataNullException(String message) {
    super(message);
  }
}
