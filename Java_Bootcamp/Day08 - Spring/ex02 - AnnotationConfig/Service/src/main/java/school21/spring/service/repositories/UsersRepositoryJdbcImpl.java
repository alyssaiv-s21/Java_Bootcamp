package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import school21.spring.service.models.User;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("Jdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository{
    private Connection conn;
    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("driveManager") DataSource ds) {
        try {
            conn = ds.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "select * from users where id = " + id;
        try {
            try(Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery(query);
                if(rs.next()) {
                    User user = new User(rs.getLong("id"),
                            rs.getString("email"), rs.getString("password"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String query = "select * from users";
        try {
            try(Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    User user = new User(rs.getLong("id"),
                            rs.getString("email"), rs.getString("password"));
                    userList.add(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    @Override
    public void save(User entity) {
        String querySave = "insert into users values (default, ?, ?)";
        try {
            try(PreparedStatement ps = conn.prepareStatement(querySave, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, entity.getEmail());
                ps.setString(2, entity.getPassword());
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong("id"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String query = "update users set email = ?, password = ? where id = ?";
        try {
            try(PreparedStatement ps = conn.prepareStatement(query)) {
                if(entity.getEmail().equals(null)) {
                    ps.setNull(1, Types.VARCHAR);
                } else {
                    ps.setString(1, entity.getEmail());
                }
                if(entity.getPassword().equals(null)) {
                    ps.setNull(2, Types.VARCHAR);
                } else {
                    ps.setString(2, entity.getPassword());
                }
                ps.setLong(3, entity.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("You can't update this entity");
        }
    }

    @Override
    public void delete(Long id) {
        String query = "delete from users where id = " + id;
        try {
            try(Statement st = conn.createStatement()) {
                st.executeUpdate(query);
            }
        } catch (SQLException ex) {
            System.out.println("You can't delete this entity");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "select * from users where email = '" + email + "'";
        try {
            try(Statement st = conn.createStatement()) {
                ResultSet rs = st.executeQuery(query);
                if(rs.next()) {
                    User user = new User(rs.getLong("id"),
                            rs.getString("email"), rs.getString("password"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @PreDestroy
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
