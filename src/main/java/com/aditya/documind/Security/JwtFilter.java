package com.aditya.documind.Security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

import com.aditya.documind.Common.ApiResponse;
import com.aditya.documind.Tenant.TenantContext;

@Component
public class JwtFilter implements Filter {

    private final JWTservice jwtService;

    public JwtFilter(JWTservice jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        String authHeader = http.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);

                Claims claims = jwtService.extractClaims(token);

                Long tenantId = claims.get("tenantId", Long.class);

                // 🔥 SET tenant automatically
                TenantContext.setTenantId(tenantId);

                String userId=claims.getSubject();
                if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userId, null,Collections.emptyList());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(http));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
