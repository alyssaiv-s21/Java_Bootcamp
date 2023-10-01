package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        UsersRepository usersRepositoryTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

        User one = new User(1L, "1@mail.ru");
        User two = new User(2L, "2@mail.ru");
        usersRepository.save(one);
        usersRepository.save(two);
        User three = new User(3L, "3@mail.ru");
        User four = new User(4L, "4@mail.ru");
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

        ((ClassPathXmlApplicationContext) context).close();
    }
}