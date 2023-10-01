package edu.school21.exceptions;

public class AlreadyAuthenticatedException extends RuntimeException {
    public AlreadyAuthenticatedException() {
        super("This user already authenticated");
    }
}
