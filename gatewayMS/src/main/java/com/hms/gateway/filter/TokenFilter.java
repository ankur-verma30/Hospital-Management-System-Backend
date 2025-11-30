package com.hms.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {
    private static final String SECRET_KEY=
            "eabd7de3ad0c09b0765b557fdb286d25840adf5e8f94489a5b1d70cbf50e0a6fb6c2febdbd18d804590da992e15194899be4475bfc01bf119fdbf550e9a2ad6e";

    public TokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) { //----> this method will check the jwt token we are receiving from
        // the user is from which route if it is from login or register then it will allow the user to access the route
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            if (path.equals("/user/login") || (path.equals("/user/register"))) {
                return chain.filter(exchange.mutate().request(r->r.header("X-Secret-Key","SECRET")).build());
            }

            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Authorization header is missing");
            }
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header is invalid");
            }
            String token = authHeader.substring(7);
            System.out.println(token);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                exchange=exchange.mutate().request(r->r.header("X-Secret-Key","SECRET")).build();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Token is invalid: " + e.getMessage());
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        //put configuration properties here
    }
}
