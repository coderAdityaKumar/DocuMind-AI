// package com.aditya.documind.Config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import com.aditya.documind.Tenant.TenantInterceptor;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @RequiredArgsConstructor
// public class WebConfig implements WebMvcConfigurer {

//     private final TenantInterceptor tenantInterceptor;

//     @Override
//     public void addInterceptors(InterceptorRegistry registry) {
//         registry.addInterceptor(tenantInterceptor)
//                 .addPathPatterns("/api/**");
//     }
// }
