package com.samsung.admin.service;

import com.samsung.admin.repository.AdminRepository;
import com.samsung.entity.Admin;
import com.samsung.util.auth.AuthUtils;
import ratpack.service.Service;

import javax.inject.Inject;

public class AdminService implements Service {
  @Inject
  private AdminRepository adminRepository;
  @Inject
  private AuthUtils authUtils;

  public void insert(Admin admin) {
    adminRepository.insert(admin);
  }

  public Admin findByUsername(String username) {
    return adminRepository.findByUsername(username);
  }

  public boolean isValidCredential(String username, String password) {
    Admin admin = findByUsername(username);
    if (admin != null) {
      return authUtils.verifyPassword(password, admin.getPassword());
    }
    return false;
  }
}
