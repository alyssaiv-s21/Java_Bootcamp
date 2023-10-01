package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

    UsersRepository userRepository;
    UsersServiceImpl service;

    final User NOT_AUTHENTICATED = new User(1L, "login", "password", false);
    final User ALREADY_AUTHENTICATED = new User(1L, "login", "password", true);

    @BeforeEach
    void initMock() {
        userRepository = mock(UsersRepository.class);
        service = new UsersServiceImpl(userRepository);
    }
    @Test
    void authenticateCorrectLoginPassword() {
        when(userRepository.findByLogin("login")).thenReturn(NOT_AUTHENTICATED);
        assertTrue(service.authenticate("login", "password"));
    }

    @Test
    void authenticateCorrectLoginNotCorrectPassword() {
        when(userRepository.findByLogin("login")).thenReturn(NOT_AUTHENTICATED);
        assertFalse(service.authenticate("login", "do_not_remember"));
    }

    @Test
    void authenticateIncorrectLogin() {
        when(userRepository.findByLogin("not_correct")).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> service.authenticate("not_correct", "password"));
    }

    @Test
    void authenticateAlreadyLogIn() {
        when(userRepository.findByLogin("login")).thenReturn(ALREADY_AUTHENTICATED);
        assertThrows(AlreadyAuthenticatedException.class, () -> service.authenticate("login", "password"));
    }

    @Test
    void authenticateUpdateException() {
        when(userRepository.findByLogin("login")).thenReturn(NOT_AUTHENTICATED);
        doThrow(EntityNotFoundException.class).when(userRepository).update(NOT_AUTHENTICATED);
        assertThrows(EntityNotFoundException.class, () -> service.authenticate("login", "password"));
    }


}
