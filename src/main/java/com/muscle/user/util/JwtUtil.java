package com.muscle.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtUtil {
    private final String SECRET_KEY = "DB@YG831GHT@";
    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes());

    public String extractUsername(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject();
    }

    public DecodedJWT decodeJWT(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    public Map<String, String> generateTokens(HttpServletRequest request, Authentication authResult) {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String access_token = createToken(request, userDetails, 1000 * 60 * 60 * 24);
        String refresh_token = createToken(request, userDetails, 1000 * 60 * 60 * 24 * 7);
        Map<String, String> tokens = new HashMap<>();

        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        return tokens;
    }

    public String createToken(HttpServletRequest request, UserDetails userDetails, Integer time) {
        log.info("EXPIRES AT: " + new Date(System.currentTimeMillis() + time));
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(ALGORITHM);
    }

    public Map<String, Object> generateErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errors = new HashMap<>();

        errors.put("timestamp", new Date(System.currentTimeMillis()).toString());
        errors.put("status", status.value());
        errors.put("error", status.getReasonPhrase());
        errors.put("message", message);

        return errors;
    }

    public static Map<String, Object> generateErrorBody(Integer status, Exception e) {
        Map<String, Object> errors = new HashMap<>();

        errors.put("timestamp", new Date(System.currentTimeMillis()).toString());
        errors.put("status", status);
        errors.put("message", e.getMessage());

        return errors;
    }

}
