package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Daniil", "Kryuchkov", (byte) 25);
        userService.saveUser("Kristina", "Kryuchkova", (byte) 28);
        userService.saveUser("Lexa", "Krasnyi", (byte) 24);
        userService.saveUser("Semen", "Vasilev", (byte) 5);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
