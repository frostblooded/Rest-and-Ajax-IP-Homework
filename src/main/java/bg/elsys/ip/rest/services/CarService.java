package bg.elsys.ip.rest.services;

import bg.elsys.ip.rest.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CarService {
    private static Session getSession() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        SessionFactory sessionFactory = instance.getSessionFactory();
        return sessionFactory.openSession();
    }

    public static void saveCar(Car car) {
        Session session = getSession();
        session.save(car);
        session.getTransaction().commit();
    }

    public static List<Car> getAll() {
        Session session = getSession();
        return session.createQuery("select c from Car as c").list();
    }
}
