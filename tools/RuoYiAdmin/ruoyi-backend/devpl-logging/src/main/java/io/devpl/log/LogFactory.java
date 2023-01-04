package io.devpl.log;

import io.devpl.log.commons.JakartaCommonsLoggingLogFactory;
import io.devpl.log.jdk14.Jdk14LoggingLogFactory;
import io.devpl.log.log4j2.Log4j2LoggingLogFactory;
import io.devpl.log.nologging.NoLoggingLogFactory;
import io.devpl.log.slf4j.Slf4jLoggingLogFactory;

/**
 * Factory for creating loggers.
 */
public class LogFactory {
    private static AbstractLogFactory theFactory;
    public static final String MARKER = "DEVPL"; //$NON-NLS-1$

    static {
        // 优先实现Slf4j门面
        tryImplementation(new Slf4jLoggingLogFactory());
        tryImplementation(new JakartaCommonsLoggingLogFactory());
        tryImplementation(new Log4j2LoggingLogFactory());
        tryImplementation(new Jdk14LoggingLogFactory());
        tryImplementation(new NoLoggingLogFactory());
    }

    private LogFactory() {
    }

    public static Log getLog(Class<?> clazz) {
        try {
            return theFactory.getLog(clazz);
        } catch (Exception t) {
            throw new RuntimeException(String.format("Error creating logger for class %s.  Cause: %s", clazz.getName(), t.getMessage()), t);
        }
    }

    /**
     * This method will switch the logging implementation to Java native
     * logging. This is useful in situations where you want to use Java native
     * logging to log activity but Log4J is on the classpath. Note that this
     * method is only effective for log classes obtained after calling this
     * method. If you intend to use this method you should call it before
     * calling any other method.
     */
    public static synchronized void forceJavaLogging() {
        setImplementation(new Jdk14LoggingLogFactory());
    }

    public static synchronized void forceSlf4jLogging() {
        setImplementation(new Slf4jLoggingLogFactory());
    }

    public static synchronized void forceCommonsLogging() {
        setImplementation(new JakartaCommonsLoggingLogFactory());
    }

    public static synchronized void forceLog4j2Logging() {
        setImplementation(new Log4j2LoggingLogFactory());
    }

    public static synchronized void forceNoLogging() {
        setImplementation(new NoLoggingLogFactory());
    }

    public static void setLogFactory(AbstractLogFactory logFactory) {
        setImplementation(logFactory);
    }

    private static void tryImplementation(AbstractLogFactory factory) {
        if (theFactory == null) {
            try {
                setImplementation(factory);
            } catch (LogException e) {
                // ignore
            }
        }
    }

    /**
     * @param factory 不同的日志工厂实现类
     */
    private static void setImplementation(AbstractLogFactory factory) {
        try {
            Log log = factory.getLog(LogFactory.class);
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + factory + "' adapter."); //$NON-NLS-1$ //$NON-NLS-2$
            }
            theFactory = factory;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t.getMessage(), t); //$NON-NLS-1$
        }
    }
}
