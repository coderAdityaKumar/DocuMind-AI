package com.aditya.documind.Config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest http = (HttpServletRequest) request;

            String requestId = UUID.randomUUID().toString();

            MDC.put("requestId", requestId);
            MDC.put("tenantId", String.valueOf(com.aditya.documind.Tenant.TenantContext.getTenantId()));
            MDC.put("path", http.getRequestURI());

            chain.doFilter(request, response);

        } finally {
            MDC.clear();
        }
    }
}