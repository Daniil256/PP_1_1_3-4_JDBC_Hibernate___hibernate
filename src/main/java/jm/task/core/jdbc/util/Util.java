package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    public SessionFactory provideSessionFactory() {
        Configuration config = new Configuration();
        config.configure();
        return config.buildSessionFactory();
    }
}