package org.jeejeejango.mt;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * @author jeejeejango
 * @since 10/01/2019 4:14 PM
 */
@Data
public class MultiTenantLiquibase implements InitializingBean, ResourceLoaderAware {

    private Collection<DataSource> dataSources;

    private Logger log = LoggerFactory.getLogger(MultiTenantLiquibase.class);

    private ResourceLoader resourceLoader;

    private String changeLog;

    private String contexts;

    private String labels;

    private Map<String, String> parameters;

    private String defaultSchema;

    private String liquibaseSchema;

    private String liquibaseTablespace;

    private String databaseChangeLogTable;

    private String databaseChangeLogLockTable;

    private boolean dropFirst;

    private boolean shouldRun = true;

    private File rollbackFile;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("DataSources based multitenancy enabled");
        }
        resolveDataSources();
        runOnAllDataSources();
    }


    private void resolveDataSources() {
        Assert.notEmpty(dataSources, "No DataSource is provided");
    }


    private void runOnAllDataSources() throws LiquibaseException {
        for (DataSource dataSource : dataSources) {
            if (log.isInfoEnabled()) {
                log.info("Initializing Liquibase for data source {}", dataSource);
            }
            SpringLiquibase liquibase = getSpringLiquibase(dataSource);
            liquibase.afterPropertiesSet();
            if (log.isInfoEnabled()) {
                log.info("Liquibase ran for data source {}", dataSource);
            }
        }
    }


    private SpringLiquibase getSpringLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLog);
        liquibase.setChangeLogParameters(parameters);
        liquibase.setContexts(contexts);
        liquibase.setLabels(labels);
        liquibase.setDropFirst(dropFirst);
        liquibase.setShouldRun(shouldRun);
        liquibase.setRollbackFile(rollbackFile);
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(defaultSchema);
        liquibase.setLiquibaseSchema(liquibaseSchema);
        liquibase.setLiquibaseTablespace(liquibaseTablespace);
        liquibase.setDatabaseChangeLogTable(databaseChangeLogTable);
        liquibase.setDatabaseChangeLogLockTable(databaseChangeLogLockTable);
        return liquibase;
    }


}
