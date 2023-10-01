package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    private Long id;
    private String name;
    private User creator;
    private List<Message> messages;

    public Room(Long id, String name, User creator, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.messages = messages;
    }

    public Room() {
        messages = new ArrayList<>();
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwnerUser() {
        return creator;
    }

    public List<Message> getListMessages() {
        return messages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerUser(User ownerUser) {
        this.creator = ownerUser;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(room.getId(), id);
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id=" + id + ",name=" + name + ",creator=" + creator.getId() + ",messages=" + messages;
    }
}
