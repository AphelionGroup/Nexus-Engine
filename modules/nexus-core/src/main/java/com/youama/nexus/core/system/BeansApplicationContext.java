package com.youama.nexus.core.system;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class provides access to resource service implementations. The visibility of the methods of this
 * BeansApplicationContext type class are package level. These methods are not available from other packages. The
 * application context is a Spring ApplicationContext.
 *
 * @author David Belicza
 * @since 0.1.0
 */
final class BeansApplicationContext {

    /**
     * The application context of all modules what are in use. These contexts from the bean configuration XMLs. All
     * contexts exist in its own thread
     */
    private ApplicationContext context;

    /**
     * It sets the application context configuration beans like database access. The application context scope based on
     * the location of the caller method.
     */
    void setApplicationContext() {
        context = new ClassPathXmlApplicationContext(getResourceEntityDeclarations());
    }

    /**
     * It retrieves the entity's service implementation by the service class name.
     *
     * @param classType
     *        Any class type what is a service class. Service classes should be defined as a Bean.
     * @return It is the service implementation. Return type is an Object what can be cast to any service class.
     */
    Object getService(Class<?> classType) {
        return context.getBean(classType);

    }

    /**
     * It retrieves the configuration Bean file(s).
     *
     * @return String array of the configuration bean XML files.
     */
    private String[] getResourceEntityDeclarations() {
        return MavenUtil.getModuleConfigBeanFiles();
    }
}
