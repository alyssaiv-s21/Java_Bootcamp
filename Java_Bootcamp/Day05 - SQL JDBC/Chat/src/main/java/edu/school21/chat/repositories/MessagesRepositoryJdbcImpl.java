package edu.school21.chat.repositories;

import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource ds;
    public MessagesRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }
    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        String queryMessage = "select * from chat.message where id = " + id;
        Message message = new Message();
        User thisUser = new User();
        Room room = new Room();
        try(Connection conn = ds.getConnection()) {
            try(Statement stmt = conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(queryMessage);
                if(resultSet.next()) {
                    message.setId(resultSet.getLong("id"));
                    Long authorId = resultSet.getLong("authorId");
                    Long roomId = resultSet.getLong("roomId");
                    message.setMessage(resultSet.getString("msg"));
                    if (resultSet.getTimestamp("dateTime") == null) {
                        message.setDateTime(null);
                    } else {
                        message.setDateTime(resultSet.getTimestamp("dateTime").toLocalDateTime());
                    }
                    String queryUser = "select * from chat.user where id = " + authorId;
                    ResultSet resultUser = stmt.executeQuery(queryUser);
                    if (resultUser.next()) {
                        thisUser.setId(authorId);
                        thisUser.setPassword(resultUser.getString("password"));
                        thisUser.setLogin(resultUser.getString("login"));
                        message.setAuthor(thisUser);
                    }
                    String queryRoom = "select * from chat.chatroom where id = " + roomId;
                    ResultSet resultRoom = stmt.executeQuery(queryRoom);
                    if (resultRoom.next()) {
                        room.setId(roomId);
                        room.setName(resultRoom.getString("name"));
                        message.setRoom(room);
                    };
                }
            }
        }
        return Optional.of(message);
    }
    @Override
    public void save(Message message) throws SQLException, NotSavedSubEntityException {
        try(Connection conn = ds.getConnection()) {
            String querySave = "insert into chat.message values (default, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(querySave, Statement.RETURN_GENERATED_KEYS);
            //preparedStatement.setLong(1, 118L);
            preparedStatement.setLong(1, message.getAuthor().getId());
            preparedStatement.setLong(2, message.getRoom().getId());
            preparedStatement.setString(3, message.getMessage());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            try {
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                }
            } catch (Exception e) {
                throw new NotSavedSubEntityException("Cant create message with this parameters");
            }
        }
    }


    @Override
    public void update(Message message) throws SQLException {
        try(Connection conn = ds.getConnection()) {
            String queryUpdate = "update chat.message set authorid = ?, roomid = ?, msg = ?, datetime = ? where id = " + message.getId();
            PreparedStatement preparedStatement = conn.prepareStatement(queryUpdate);
            if(message.getAuthor() == null) {
                preparedStatement.setNull(1, Types.BIGINT);
            } else {
                preparedStatement.setLong(1, message.getAuthor().getId());
            }
            if(message.getRoom() == null) {
                preparedStatement.setNull(2, Types.BIGINT);
            } else {
                preparedStatement.setLong(2, message.getRoom().getId());
            }
            if(message.getMessage() == null) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else {
                preparedStatement.setString(3,  message.getMessage());
            }
            if(message.getDateTime() == null) {
                preparedStatement.setNull(4, Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            }
            int rs = preparedStatement.executeUpdate();
            if(rs != 0) {
                System.out.println("Message was updated");
            }
        }
    }
}








