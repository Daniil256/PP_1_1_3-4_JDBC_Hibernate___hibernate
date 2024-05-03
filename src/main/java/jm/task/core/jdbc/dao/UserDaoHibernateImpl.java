package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    Util util = new Util();


    public void createUsersTable() {
        Session session = util.openSession();
        util.closeSession(session);
    }


    public void saveUser(String name, String lastName, byte age) {
        try (Session session = util.openSession()) {
            session.persist(new User(name, lastName, age));
            util.closeSession(session);
            System.out.printf("User с именем — %s добавлен в базу данных \n", name);
        } catch (Exception e) {
            System.out.println("Ошибка в saveUser " + e);
        }
    }

    public void removeUserById(long id) {
        try (Session session = util.openSession()) {
            User user = session.get(User.class, id);
            session.remove(user);
            util.closeSession(session);
        } catch (Exception e) {
            System.out.println("Ошибка в removeUserById " + e);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = util.openSession()) {
            List<User> userList = new java.util.ArrayList<>(List.of());
            Object[] list = session.createNativeQuery("SELECT * FROM users;")
                    .stream()
                    .toArray();
            Arrays.stream(list).forEach(el -> {
                User user = new User();
                String[] arr = Arrays.toString((Object[]) el)
                        .replaceAll("[\\[\\],]", "")
                        .split(" ");
                user.setId(Long.valueOf(arr[0]));
                user.setName(arr[3]);
                user.setLastName(arr[2]);
                user.setAge(Byte.valueOf(arr[1]));
                userList.add(user);
            });
            util.closeSession(session);
            return userList;
        } catch (Exception e) {
            System.out.println("Ошибка в getAllUsers " + e);
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Session session = util.openSession()) {
            Transaction t = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            t.commit();
            util.closeSession(session);
        } catch (Exception e) {
            System.out.println("Ошибка в cleanUsersTable " + e);
        }
    }

    public void dropUsersTable() {
        try (Session session = util.openSession()) {
            Transaction t = session.beginTransaction();
            session.createNativeQuery("drop table users;").executeUpdate();
            t.commit();
            util.closeSession(session);
        } catch (Exception e) {
            System.out.println("Ошибка в dropUsersTable " + e);
        }
    }
}
