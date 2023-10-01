package edu.school21.models.car;

import java.util.StringJoiner;

public class Car {
    private String name;
    private boolean isStarted;
    private Long speed;

    public Car() {
        name = "Example";
        isStarted = false;
        speed = 0L;
    }

    public Car(String name, boolean isStarted, Long speed) {
        this.name = name;
        this.isStarted = isStarted;
        this.speed = speed;
    }

    public void speedUp(Long value, boolean start) {
        isStarted = start;
        if(isStarted && value > 0) {
            speed += value;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("Name='" + name + "'")
                .add("is Started='" + isStarted + "'")
                .add("speed=" + speed)
                .toString();
    }
}
