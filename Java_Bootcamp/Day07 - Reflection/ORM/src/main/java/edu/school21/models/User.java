package edu.school21.models;

import edu.school21.annotation.OrmColumn;
import edu.school21.annotation.OrmColumnId;
import edu.school21.annotation.OrmEntity;

import java.util.Objects;

@OrmEntity(table = "simple_user")
public class User {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name", length = 10)
    private String firstName;
    @OrmColumn(name = "last_name", length = 10)
    private String lastName;
    @OrmColumn(name = "age")
    private Integer age;

    public User() {
        id = 0L;
        firstName = "Unknown";
        lastName = "Unknown";
        age = 0;
    }

    public User(Long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Integer getAge() { return age; }
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(Integer age) { this.age = age; }
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.getId() && firstName.equals(user.getFirstName())
                && lastName.equals(user.getLastName()) && age == user.getAge();
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age);
    }

    @Override
    public String toString() {

        return "User with id " + id + ", name " + firstName + ", last name "
                + lastName + ", age " + age;
    }
}
