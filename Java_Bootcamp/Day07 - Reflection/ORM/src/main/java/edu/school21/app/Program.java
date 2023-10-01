package edu.school21.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.datasource.HikariDS;
import edu.school21.manager.OrmManager;
import edu.school21.models.Car;
import edu.school21.models.User;

import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {
        DataSource ds = HikariDS.getDataSource();
        OrmManager ormManager = new OrmManager(ds);
        System.out.println("Drop and create tables:");
        ormManager.initialize();
        System.out.println("\nAdd 3 users in User table:");
        User userOne = new User(null, "Anna", "Black", 20);
        ormManager.save(userOne);
        User userTwo = new User(null, "Inna", "White", 21);
        ormManager.save(userTwo);
        User userThree = new User(null, "Olga", "Pink", 22);
        ormManager.save(userThree);
        System.out.println("\nUser Olga after save in DB");
        System.out.println(userThree);
        System.out.println("\nUpdate user Anna in User table:");
        userOne.setAge(55);
        userOne.setLastName(null);
        ormManager.update(userOne);

        System.out.println("\nFind user by id in User table:");
        User needToFind = ormManager.findById(1L, User.class);
        System.out.println(needToFind);
        User needToFind2 = ormManager.findById(2L, User.class);
        System.out.println(needToFind2);

        System.out.println("\n\nAdd 3 cars in Car table:");
        Car carOne = new Car(0L, "sedan", false, 0L);
        Car carTwo = new Car(0L, "minivan", true, 85L);
        Car carThee = new Car(0L, "coupe", true, 25L);
        ormManager.save(carOne);
        ormManager.save(carTwo);
        ormManager.save(carThee);
        System.out.println("\nUpdate car 3 in Car table:");
        carThee.setIsStarted(false);
        carThee.setSpeed(null);
        ormManager.update(carThee);
        System.out.println("\nFind car by id in Car table:");
        Car toFind = ormManager.findById(2L, Car.class);
        System.out.println(toFind);
        Car toFind2 = ormManager.findById(3L, Car.class);
        System.out.println(toFind2);

        ((HikariDataSource)ds).close();
    }
}
