package com.msb.insurance.pob.jwt;

import com.msb.insurance.pob.service.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@Getter
public class JwtTokenProvider {
    private String JWT_SECRET = "pob_token_@@@!!!";
    private int JWT_EXPIRATION = 60000;
    private int JWT_FRESH_EXPIRATION = 300000;

    // Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetail customUserDetail) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ username của user.
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public String generateFreshToken(CustomUserDetail customUserDetail) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_FRESH_EXPIRATION);
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUsernameFromJWT(String token) {
        String username = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            log.error("Invalid JWT signature -> Message: " + e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: " + e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: " + e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: " + e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: " + e);
        }
        return false;
    }
}
