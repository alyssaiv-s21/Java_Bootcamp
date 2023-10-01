package edu.school21.chat.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    final static DateTimeFormatter TO_SQL = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    private Long id;
    private User author;
    private Room room;
    private String message;
    private LocalDateTime dateTime;

    public Message(Long id, User author, Room room, String message, LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Message() {}
    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Room getRoom() {
        return room;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message msg = (Message) o;
        return Objects.equals(msg.getId(), id);
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "Message : {\n\tid=" + id + ",\n\tauthor={" + author + "},\n\troom={"
                + room + "},\n\ttext=\"" + message + "\",\n\tdateTime=" + dateTime.format(CUSTOM_FORMATTER) + "\n}";
    }
}
