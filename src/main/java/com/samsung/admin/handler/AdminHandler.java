package com.samsung.admin.handler;

import com.samsung.admin.core.request.AdminRequest;
import com.samsung.admin.core.response.AdminLoginResponse;
import com.samsung.admin.service.AdminService;
import com.samsung.entity.Admin;
import com.samsung.util.auth.AuthUtils;
import com.samsung.util.response.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;

import javax.inject.Inject;

public class AdminHandler {
  @Inject
  private AdminService adminService;
  @Inject
  private AuthUtils authUtils;

  public void handleLogin(Context ctx) {
    ctx.parse(Jackson.fromJson(AdminRequest.class)).then(req -> {
      if (StringUtils.isBlank(req.username) || StringUtils.isBlank(req.password)) {
        ResponseUtil.badRequestResponse(ctx, "username or password can't be blank");
        return;
      }
      String username = req.username;
      String password = req.password;
      if (adminService.isValidCredential(username, password)) {
        String token = authUtils.generateToken(username);
        ResponseUtil.okResponse(ctx, AdminLoginResponse.from(token));
      } else {
        ResponseUtil.badRequestResponse(ctx, "username or password is incorrect");
      }
    });
  }

  public void postAdmin(Context ctx) {
    ctx.parse(Jackson.fromJson(AdminRequest.class)).then(req -> {
      if (StringUtils.isBlank(req.username) || StringUtils.isBlank(req.password)) {
        ResponseUtil.badRequestResponse(ctx, "username or password can't be blank");
        return;
      }
      String username = req.username;
      String password = authUtils.hashPassword(req.password);
      Admin newAdmin = new Admin();
      newAdmin.setUsername(username);
      newAdmin.setPassword(password);
      adminService.insert(newAdmin);
      ResponseUtil.createdResponse(ctx, true);
    });
  }
}
