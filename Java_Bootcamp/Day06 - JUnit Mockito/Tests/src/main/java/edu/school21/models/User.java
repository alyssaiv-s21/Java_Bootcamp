package edu.school21.models;

import java.util.Objects;

public class User {

    private Long id;
    private String login;
    private String password;
    private boolean authStatus;

    public User(Long id, String login, String password, boolean authStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authStatus = authStatus;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public boolean getAuthStatus() { return authStatus; }

    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.getId() && login.equals(user.getLogin())
                && password.equals(user.getPassword()) && getAuthStatus() == user.getAuthStatus();
    }

    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        return "id = " + id + ", login = " + login + ", password = " + password;
    }


}
