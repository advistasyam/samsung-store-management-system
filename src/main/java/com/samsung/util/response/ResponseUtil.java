package com.samsung.util.response;

import ratpack.handling.Context;
import ratpack.jackson.Jackson;

import java.util.Date;

public class ResponseUtil {
  public static SamsungResponse.Status okStatus() {
    return new SamsungResponse.Status(200, new Date(), null);
  }

  public static SamsungResponse.Status createdStatus() {
    return new SamsungResponse.Status(201, new Date(), null);
  }

  public static SamsungResponse.Status badRequestStatus(String message) {
    return new SamsungResponse.Status(400, new Date(), message);
  }

  public static SamsungResponse.Status unauthorizedStatus(String message) {
    return new SamsungResponse.Status(401, new Date(), message);
  }

  public static void okResponse(Context ctx, Object response) {
    SamsungResponse.Status status = okStatus();
    SamsungResponse<?> responseObj = new SamsungResponse<>(status, response);
    ctx.getResponse().status(status.code);
    ctx.render(Jackson.json(responseObj));
  }

  public static void createdResponse(Context ctx, Object response) {
    SamsungResponse.Status status = createdStatus();
    SamsungResponse<?> responseObj = new SamsungResponse<>(status, response);
    ctx.getResponse().status(status.code);
    ctx.render(Jackson.json(responseObj));
  }

  public static void badRequestResponse(Context ctx, String message) {
    SamsungResponse.Status status = badRequestStatus(message);
    SamsungResponse<?> responseObj = new SamsungResponse<>(status, null);
    ctx.getResponse().status(status.code);
    ctx.render(Jackson.json(responseObj));
  }

  public static void unauthorizedResponse(Context ctx, String message) {
    SamsungResponse.Status status = unauthorizedStatus(message);
    SamsungResponse<?> responseObj = new SamsungResponse<>(status, null);
    ctx.getResponse().status(status.code);
    ctx.render(Jackson.json(responseObj));
  }
}
