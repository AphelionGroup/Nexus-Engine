package com.youama.nexus.core.system;

import org.junit.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * @author David Belicza
 * @since 0.1.0
 */
public class ServiceUtilTest {

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testServiceUtilsLife() {

        assertTrue(ServiceUtil.getDefaultDBDriver().length() > 0);

        assertNull(ServiceUtil.getDBDriver());
        assertNull(ServiceUtil.getDBUrl());
        assertNull(ServiceUtil.getDBUser());
        assertNull(ServiceUtil.getDBPassword());
        assertNull(ServiceUtil.getDBDialect());
        assertNull(ServiceUtil.getDBCreation());
        assertNull(ServiceUtil.getDBSessionContext());
        assertNull(ServiceUtil.getDBLog());

        assertTrue(ServiceUtil.getInstalledDrivers().size() == 0);

        // BeanDefinitionStoreException because there is no primary artifact ID - the configuration.
        ServiceUtil.enableServiceDriver();

        // No exception, system loaded thought core module.
        Configuration.getInstance().registerPrimaryModule("nexus-module-core", ServiceUtil.class);
        ServiceUtil.enableServiceDriver();

        assertTrue(ServiceUtil.getInstalledDrivers().size() > 0);

        ServiceUtil.switchDriver("fake");
        ServiceUtil.switchDriver(ServiceUtil.getDefaultDBDriver());

        // Non-exists service - NoSuchBeanDefinitionException
        assertNull(ServiceUtil.getService(Objects.class));

        assertEquals(SystemConstant.DATABASE_SERVER, ServiceUtil.getDatasourceId());

        System.out.println(ServiceUtil.getDBUser());
        System.out.println(ServiceUtil.getDBPassword());
        assertTrue(ServiceUtil.getDefaultDBDriver().length() > 0);
        assertTrue(ServiceUtil.getDBDriver().length() > 0);
        assertTrue(ServiceUtil.getDBUrl().length() > 0);
        assertTrue(ServiceUtil.getDBUser().length() > 0);
        assertTrue(ServiceUtil.getDBPassword().length() > 0);
        assertTrue(ServiceUtil.getDBDialect().length() > 0);
        assertTrue(ServiceUtil.getDBCreation().length() > 0);
        assertTrue(ServiceUtil.getDBSessionContext().length() > 0);
        assertTrue(ServiceUtil.getDBLog().length() > 0);

        // It removes the local thread singleton what contains the drivers.
        ServiceUtil.disableServiceDriver();
        assertTrue(ServiceUtil.getInstalledDrivers().size() == 0);
    }

    @After
    public void tierDown() {
        ServiceUtil.disableServiceDriver();
        Configuration.resetInstance();
    }
}