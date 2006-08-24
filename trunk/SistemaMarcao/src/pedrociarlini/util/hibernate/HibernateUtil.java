package pedrociarlini.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static Session getSession() throws HibernateException {
        if (sessionFactory == null) {
            try {
                sessionFactory = new AnnotationConfiguration().configure()
                        .buildSessionFactory();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return sessionFactory.openSession();
    }
}