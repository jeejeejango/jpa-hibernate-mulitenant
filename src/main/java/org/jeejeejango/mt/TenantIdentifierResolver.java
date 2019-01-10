package org.jeejeejango.mt;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import static org.jeejeejango.Constants.DEFAULT_TENANT_ID;

/**
 * @author jeejeejango
 * @since 09/01/2019 16:09
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantHolder.getTenantId() != null ? TenantHolder.getTenantId() : DEFAULT_TENANT_ID;
    }


    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }


}
