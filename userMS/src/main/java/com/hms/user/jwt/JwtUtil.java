package com.hms.user.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private  static final long JWT_TOKEN_VALIDITY = 5*60*60L;
    private static final String SECRET_KEY=
            "eabd7de3ad0c09b0765b557fdb286d25840adf5e8f94489a5b1d70cbf50e0a6fb6c2febdbd18d804590da992e15194899be4475bfc01bf119fdbf550e9a2ad6e";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        claims.put("id",customUserDetails.getId());
        claims.put("email",customUserDetails.getEmail());
        claims.put("role",customUserDetails.getRole());
        claims.put("name",customUserDetails.getName());
        claims.put("profileId",customUserDetails.getProfileId());
        return doGenerateToken(claims,customUserDetails.getUsername());
    }

    public String doGenerateToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

}
