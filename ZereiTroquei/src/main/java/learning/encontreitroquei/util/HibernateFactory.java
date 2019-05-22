package learning.encontreitroquei.util;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateFactory {

    private static SessionFactory sessionFactory;

    public static SessionFactory buildSessionFactory() {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    public static SessionFactory buildIfNeeded() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        return configureSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
        buildIfNeeded();
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void close(Session session) {
        if (session != null) {
            session.close();
        }
    }

    public static void rollback(Transaction tx) {
        if (tx != null) {
            tx.rollback();
        }
    }

    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
}
