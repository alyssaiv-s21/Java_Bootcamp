package edu.school21.models;

import edu.school21.annotation.OrmColumn;
import edu.school21.annotation.OrmColumnId;
import edu.school21.annotation.OrmEntity;

import java.util.Objects;

@OrmEntity(table = "simple_car")
public class Car {

    @OrmColumnId
    private Long id;

    @OrmColumn(name = "name", length = 25)
    private String name;

    @OrmColumn(name = "isStarted")
    private boolean isStarted;

    @OrmColumn(name = "speed")
    private Long speed;
    public Long getId() { return id; }
    public String getName() { return name; }
    public Boolean getIsStarted() { return isStarted; }
    public Long getSpeed() { return speed; }
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setIsStarted(Boolean isStarted) { this.isStarted = isStarted; }
    public void setSpeed(Long speed) { this.speed = speed; }

    public Car() {
        id = 0L;
        name = "Example";
        isStarted = false;
        speed = 0L;
    }

    public Car(Long id, String name, boolean isStarted, Long speed) {
        this.id = id;
        this.name = name;
        this.isStarted = isStarted;
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.getId() && name.equals(car.getName())
                && isStarted == car.getIsStarted() && speed == car.getSpeed();
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, isStarted, speed);
    }

    @Override
    public String toString() {
        return "Car with id " + id + ", name " + name + ", is started "
                + isStarted + ", with speed " + speed;
    }

}
