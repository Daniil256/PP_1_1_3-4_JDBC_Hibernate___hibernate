package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    Util util = new Util();

    public void createUsersTable() {
        SessionFactory sessionFactory = util.provideSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        t.commit();
        sessionFactory.close();
    }

    public void dropUsersTable() {
        SessionFactory sessionFactory = util.provideSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.createNativeQuery("drop table users;").executeUpdate();
        t.commit();
        sessionFactory.close();
    }

    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = util.provideSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(new User(name, lastName, age));
        t.commit();
        System.out.printf("User с именем — %s добавлен в базу данных \n", name);
        sessionFactory.close();
    }

    public void removeUserById(long id) {
        SessionFactory sessionFactory = util.provideSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user == null) {
            System.out.println("Пользователь не найден");
        } else {
            session.remove(user);
            t.commit();
            sessionFactory.close();
        }
    }

    public List<User> getAllUsers() {
        SessionFactory sessionFactory = util.provideSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        Object[] list = session.createNativeQuery("SELECT * FROM users;").stream().toArray();
        List<User> userList = new java.util.ArrayList<>(List.of());
        Arrays.stream(list).forEach(el -> {
            User user = new User();
            String[] arr = Arrays.toString((Object[]) el).replaceAll("[\\[\\],]", "").split(" ");
            user.setId(Long.valueOf(arr[0]));
            user.setName(arr[3]);
            user.setLastName(arr[2]);
            user.setAge(Byte.valueOf(arr[1]));
            userList.add(user);
        });

        t.commit();
        sessionFactory.close();
        return userList;
    }

    public void cleanUsersTable() {
        SessionFactory sessionFactory = util.provideSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        t.commit();
        sessionFactory.close();
    }
}
