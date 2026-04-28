package com.aditya.documind.Tenant;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantContext {
    public static final ThreadLocal<Long> CURRENT_TENANT=new ThreadLocal<>();

    public static void setTenantId(Long tenantId){
        CURRENT_TENANT.set(tenantId);
    }

    public static Long getTenantId(){
        return CURRENT_TENANT.get();
    }

    public static void clear(){
        CURRENT_TENANT.remove();
    }
}
