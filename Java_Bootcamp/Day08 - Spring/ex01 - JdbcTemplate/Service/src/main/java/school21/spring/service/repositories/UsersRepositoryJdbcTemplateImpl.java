package school21.spring.service.repositories;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject("select * from users where id = ?",
                    new BeanPropertyRowMapper<>(User.class), id);
            return Optional.of(user);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("insert into users values (?, ?)", entity.getId(), entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("update users set email = ? where id = ?", entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from users where id = ?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject("select * from users where email = ?",
                    new BeanPropertyRowMapper<>(User.class), email);
            return Optional.of(user);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

}
