// package com.aditya.documind.Tenant;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;
// import org.hibernate.Session;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.springframework.stereotype.Component;

// @Aspect
// @Component
// public class TenantAspect {

//     @PersistenceContext
//     private EntityManager entityManager;

//     @Before("execution(* com.aditya.documind.Service..*(..))")
//     public void beforeServiceMethod() {
//         Long tenantId = TenantContext.getTenantId();
//         if (tenantId != null) {
//             Session session = entityManager.unwrap(Session.class);
//             session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
//         }
//     }
// }
