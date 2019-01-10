package org.jeejeejango.mt;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import javax.sql.DataSource;

/**
 * @author jeejeejango
 * @since 09/01/2019 16:09
 */
public class DataSourceMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private MultiTenantDataSources multiTenantDataSources;


    public DataSourceMultiTenantConnectionProvider(MultiTenantDataSources multiTenantDataSources) {
        this.multiTenantDataSources = multiTenantDataSources;
    }


    @Override
    protected DataSource selectAnyDataSource() {
        return this.multiTenantDataSources.getDefault();
    }


    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return this.multiTenantDataSources.get(tenantIdentifier);
    }


}
