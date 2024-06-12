package com.samsung.util.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.entity.Admin;
import com.samsung.util.response.ResponseUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import ratpack.handling.Context;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthUtils {
  private static final String SECRET_KEY = "bang-pesen-mie-ayam-1-tambah-kecap-tambah-saos-ga-pake-lama";
  private static final long EXPIRATION_TIME = 86400000; // 1 day
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public String generateToken(Admin admin) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

    Map<String, Object> claims = new HashMap<>();
    claims.put("payload", admin);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(admin.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(expirationDate)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Admin parseToken(Context ctx) {
    String authHeader = ctx.getRequest().getHeaders().get("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    String token = authHeader.substring(7);
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    Object payload = claims.get("payload");

    return MAPPER.convertValue(payload, Admin.class);
  }

  public boolean validateToken(String token) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isNotLogin(Context ctx) {
    String authHeader = ctx.getRequest().getHeaders().get("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      if (validateToken(token)) {
        return false;
      } else {
        ResponseUtil.unauthorizedResponse(ctx, "login token is not valid, please login again");
        return true;
      }
    } else {
      ResponseUtil.unauthorizedResponse(ctx, "Missing or invalid Authorization header");
      return true;
    }
  }

  public String hashPassword(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
  }

  public boolean verifyPassword(String plainPassword, String hashedPassword) {
    return BCrypt.checkpw(plainPassword, hashedPassword);
  }
}