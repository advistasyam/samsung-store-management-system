package com.samsung.admin.core.response;

public class AdminLoginResponse {
  public String jwtToken;

  public static AdminLoginResponse from(String jwtToken) {
    AdminLoginResponse response = new AdminLoginResponse();
    response.jwtToken = jwtToken;
    return response;
  }
}
