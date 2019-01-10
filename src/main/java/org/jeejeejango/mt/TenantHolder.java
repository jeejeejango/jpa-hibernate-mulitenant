package org.jeejeejango.mt;

/**
 * @author jeejeejango
 * @since 09/01/2019 16:09
 */
public class TenantHolder {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();


    public static void setTenantId(String tenantId) {
        TENANT.set(tenantId);
    }


    public static String getTenantId() {
        return TENANT.get();
    }


    public static void clear() {
        TENANT.remove();
    }


}
