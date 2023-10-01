package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private DataSource ds;
    public UsersRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }
    @Override
    public List<User> findAll(int page, int size) throws SQLException {
        List<User> userList = new ArrayList<>();
        Map<Long, Room> roomList = new HashMap<>();
        String queryFindAll = "(with needed_user as (select * from chat.user where id >= ? and id <= ?)\n" +
                "select needed_user.id as userid, login, password, r.id as roomid, name, ownerid, 1 as isowner\n" +
                "from needed_user \n" +
                "left join chat.chatroom as r on needed_user.id = r.ownerid)\n" +
                "union\n" +
                "(with needed_user as (select * from chat.user where id >= ? and id <= ?)\n" +
                "select needed_user.id as userid, login, password, roomid, name, ownerid, 0 as isowner\n" +
                "from needed_user \n" +
                "join chat.message as m on needed_user.id = m.authorid \n" +
                "join chat.chatroom as r on roomid = r.id)\n" +
                "order by userid, roomid";

        try(Connection conn = ds.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(queryFindAll);
            preparedStatement.setInt(1, page * size + 1);
            preparedStatement.setInt(2, (page * size + size));
            preparedStatement.setInt(3, page * size + 1);
            preparedStatement.setInt(4, (page * size + size));
            ResultSet set = preparedStatement.executeQuery();
            Long userId = 0L;
            while (set.next()) {
                if(set.getLong("userid") != userId) {
                    userId = set.getLong("userid");
                    String login = set.getString("login");
                    String password = set.getString("password");
                    List<Room> allRooms = new ArrayList<>();
                    List<Room> userRooms = new ArrayList<>();
                    User user = new User(userId, login, password, allRooms, userRooms);
                    userList.add(user);
                }
                Long roomId = set.getLong("roomid");
                if(!roomList.containsKey(roomId)) {
                    String name = set.getString("name");
                    Long ownerId = set.getLong("ownerid");
                    User creator = new User();
                    creator.setId(ownerId);
                    List<Message> listMsg = new ArrayList<>();
                    Room room = new Room(roomId, name, creator, listMsg);
                    roomList.put(roomId, room);
                }
                if(set.getInt("isowner") == 1 && roomId != 0) {
                    userList.get(userList.size() - 1).addUserRooms(roomList.get(roomId));
                } else {
                    userList.get(userList.size() - 1).addAllRooms(roomList.get(roomId));
                }

            }
        }
        return userList;
    }
}
