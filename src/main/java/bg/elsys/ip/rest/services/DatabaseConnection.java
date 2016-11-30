package bg.elsys.ip.rest.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseConnection {
    private static DatabaseConnection ourInstance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return ourInstance;
    }

    public SessionFactory sessionFactory;
    public ServiceRegistry serviceRegistry;

    private DatabaseConnection() {
        Configuration configuration = new Configuration().configure();
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}
