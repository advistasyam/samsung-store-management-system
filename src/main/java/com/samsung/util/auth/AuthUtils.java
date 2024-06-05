package com.samsung.util.auth;

import com.samsung.util.response.ResponseUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import ratpack.handling.Context;

import java.security.Key;
import java.util.Date;

public class AuthUtils {
  private static final String SECRET_KEY = "bang-pesen-mie-ayam-1-tambah-kecap-tambah-saos-ga-pake-lama";
  private static final long EXPIRATION_TIME = 86400000; // 1 day

  public String generateToken(String username) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expirationDate)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
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