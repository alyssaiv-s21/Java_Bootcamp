package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/alyssaiv");
        config.setUsername("alyssaiv");
        config.setPassword("");
        HikariDataSource ds = new HikariDataSource(config);

        UsersRepository messagesRepository = new UsersRepositoryJdbcImpl(ds);

        try {
            List<User> result = messagesRepository.findAll(0, 4);
            for(User u : result) {
                System.out.println(u.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
