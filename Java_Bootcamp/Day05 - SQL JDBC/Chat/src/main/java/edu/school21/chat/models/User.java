package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Room> allRooms;
    private List<Room> userRooms;

    public User(Long id, String login, String password, List<Room> allRooms, List<Room> userRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.allRooms = allRooms;
        this.userRooms = userRooms;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public List<Room> getAllRooms() {
        return allRooms;
    }
    public List<Room> getUserRooms() {
        return userRooms;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void addUserRooms(Room room) {
        userRooms.add(room);
    }
    public void addAllRooms(Room room) {
        allRooms.add(room);
    }

    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user.getId(), id);
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id=" + id + ",login=" + login + ",password=" + password
                + ",createdRooms=" + userRooms + ",rooms=" + allRooms;
    }
}
