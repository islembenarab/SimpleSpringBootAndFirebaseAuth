package com.firebaseproject.configuration;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.annotations.NotNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class FirebaseFilterToken extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request
            , @NonNull HttpServletResponse response
            , @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader != null) {
            if (authenticationHeader.startsWith("Bearer ")) {
                FirebaseToken decodedToken;
                try {
                    String token = authenticationHeader.substring(7);
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                    GrantedAuthority grantedAuthority=new SimpleGrantedAuthority((String) decodedToken.getClaims().get("role"));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(decodedToken.getEmail(),
                            token,
                            Collections.singleton(grantedAuthority));
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (FirebaseAuthException e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error! " + e);
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
