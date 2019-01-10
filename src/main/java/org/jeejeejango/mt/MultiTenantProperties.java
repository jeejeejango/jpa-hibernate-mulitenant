package org.jeejeejango.mt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author jeejeejango
 * @since 09/01/2019 16:09
 */
@ConfigurationProperties(prefix = "tenant")
@Data
public class MultiTenantProperties {

    private String defaultTenantId;

    private boolean liquibaseEnabled;

    private List<TenantDataSourceProperties> dataSources;


    public static class TenantDataSourceProperties extends DataSourceProperties {

        @Getter
        @Setter
        private String tenantId;

    }

}
