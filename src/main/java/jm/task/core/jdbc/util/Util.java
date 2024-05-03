package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Util {

    public Session openSession() {
        return new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();
    }

    public void closeSession(Session session) {
        session.beginTransaction().commit();
        session.close();
    }
}