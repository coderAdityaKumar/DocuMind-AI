// package com.aditya.documind.Tenant;

// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class TenantInterceptor implements HandlerInterceptor {
//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//         String tenantId = request.getHeader("X-Tenant-ID");
//         if (tenantId != null && !tenantId.isEmpty()) {
//             TenantContext.setTenantId(Long.parseLong(tenantId));
//         }
//         return true;
//     }

//     @Override
//     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
//             Exception ex) {
//         TenantContext.clear();
//     }
// }
