package school21.spring.service.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersServiceImpl;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("school21.spring.service");
        context.refresh();

        UsersRepository usersRepository = context.getBean(UsersRepositoryJdbcImpl.class);
        UsersRepository usersRepositoryTemplate = context.getBean(UsersRepositoryJdbcTemplateImpl.class);

        User one = new User(1L, "1@mail.ru", "123");
        User two = new User(2L, "2@mail.ru", "123");
        usersRepository.save(one);
        usersRepository.save(two);
        User three = new User(3L, "3@mail.ru", "123");
        User four = new User(4L, "4@mail.ru", "123");
        usersRepositoryTemplate.save(three);
        usersRepositoryTemplate.save(four);

        one.setEmail("something@mail.ru");
        two.setEmail("newemail@mail.ru");
        usersRepository.update(two);
        usersRepositoryTemplate.update(one);

        usersRepository.delete(3L);
        usersRepositoryTemplate.delete(4L);
        System.out.println(usersRepository.findById(1L));
        System.out.println(usersRepositoryTemplate.findById(2L));
        System.out.println(usersRepository.findById(111L));
        System.out.println(usersRepositoryTemplate.findById(211L));
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findByEmail("newemail@mail.ru"));
        System.out.println(usersRepositoryTemplate.findByEmail("newemail@mail.ru"));

        UsersServiceImpl usersService = context.getBean(UsersServiceImpl.class);
        System.out.println(usersService.signUp("123@mail.ru"));

        context.close();
    }
}