package edu.school21.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
        super("There is no user with this login");
    }
}
