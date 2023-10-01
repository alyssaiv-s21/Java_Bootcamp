package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService{

    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("JdbcTemplate") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String password = UUID.randomUUID().toString();
        usersRepository.save(new User(0L, email, password));
        Optional<User> user = usersRepository.findByEmail(email);
        if(user.isEmpty()) {
            return null;
        } else {
            return user.get().getPassword();
        }
    }
}
