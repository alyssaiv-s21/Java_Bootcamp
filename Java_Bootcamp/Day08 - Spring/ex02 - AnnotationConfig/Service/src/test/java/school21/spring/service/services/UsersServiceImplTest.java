package school21.spring.service.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {
    private UsersService usersService;

    private AnnotationConfigApplicationContext context;
    @BeforeEach
    void initDB() {
        context = new AnnotationConfigApplicationContext();
        context.scan("school21.spring.service");
        context.refresh();
        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl((DataSource) context.getBean("embeddedDS"));
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    void signUpNormal() {
        String result = usersService.signUp("123@mail.ru");
        System.out.println("\nCreated password: " + result);
        assertNotNull(result);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

}
