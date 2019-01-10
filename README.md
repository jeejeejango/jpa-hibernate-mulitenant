# Hibernate/JPA Multi-tenancy Sample
This is a Spring-Boot sample to demonstrate on how to archive multi-tenancy in the
Hibernate/JPA environment. As Hibernate does not support schema export in multi-tenancy, Liquibase is used to 
manage the schema.

## DataSources configuration
In this sample, the datasources are not created by Spring-Boot auto configuration. 
When multiple datasources are used, you will have to initialize it manually.

A new configuration class [MultiTenantProperties](src/main/java/org/jeejeejango/mt/MultiTenantProperties.java) 
is used to define the default tenant, as well as the tenant datasources. It also 
allows define if Liquibase should be executed when the application starts.

## Liquibase configuration
In Liquibase auto configuration, only a single datasource is supported. You will 
need to turn off the auto configuration using the properties `spring.liquibase.enabled = false` 
to disable the default run. If you will like to test this using embedded database, then you 
can ignore this setting off.

The multi-tenancy Liquibase is referenced from MultiTenantSpringLiquibase class, with
the difference of injecting the datasources directly instead of lookup from the JDNI 
tree. The same changelogs are applied to all the configured datasources.

All other Liquibase settings can be configured under Spring-Boot `spring.liquibase.*`.

## Tenant Selector
In order to pass the Tenant Id to Hibernate, [TenantHolder](src/main/java/org/jeejeejango/mt/TenantHolder.java) 
hold a ThreadLocal variable for the tenant state management. In a typically web application, 
Tenant Id is kept in the user session. To inject the Tenant Id to the TenantHolder, you 
can leverage on Spring HandlerInterceptorAdapter to set it before execution, and clear 
it after completion. 