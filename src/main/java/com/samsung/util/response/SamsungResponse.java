package com.samsung.util.response;

import java.util.Date;

public class SamsungResponse<T> {
  public Status status;
  public T body;

  public static class Status {
    public int code;
    public Date serverResponseTime;
    public String errMessage;

    public Status(int code, Date serverResponseTime, String errMessage) {
      this.code = code;
      this.serverResponseTime = serverResponseTime;
      this.errMessage = errMessage;
    }
  }

  public SamsungResponse(Status status, T body) {
    this.status = status;
    this.body = body;
  }
}