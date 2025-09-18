package com.api.service.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.api.exception.UserNotFoundException;
import com.api.model.User;
import com.api.repo.UserRepository;
import com.api.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    private User user1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User(1, "Karthik", "Karthik@example.com");
    }

    @Test
    void testSaveUserDetails() {
        when(repo.save(user1)).thenReturn(user1);

        User savedUser = service.saveUserDetails(user1);

        assertNotNull(savedUser);
        assertEquals("Karthik", savedUser.getUname());
        verify(repo, times(1)).save(user1);
    }

    @Test
    void testGetOneUser_UserFound() {
        when(repo.findById(1)).thenReturn(Optional.of(user1));

        User result = service.getOneUser(1);

        assertNotNull(result);
        assertEquals("Karthik", result.getUname());
        verify(repo, times(1)).findById(1);
    }

    @Test
    void testGetOneUser_UserNotFound() {
        when(repo.findById(2)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.getOneUser(2));
        verify(repo, times(1)).findById(2);
    }

    @Test
    void testGetUserDetails() {
        List<User> users = Arrays.asList(user1, new User(2, "Renu", "renu@example.com"));
        when(repo.findAll()).thenReturn(users);

        List<User> result = service.getUserDetails();

        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testDeleteOneUser() {
        doNothing().when(repo).deleteById(1);

        service.deleteOneUser(1);

        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteAllUserDetails() {
        doNothing().when(repo).deleteAll();

        service.deleteAllUserDetails();

        verify(repo, times(1)).deleteAll();
    }

    @Test
    void testUpdateUserDetails_UserFound() {
        User updatedUser = new User(1, "John", "john@example.com");

        when(repo.findById(1)).thenReturn(Optional.of(user1));
        when(repo.save(any(User.class))).thenReturn(updatedUser);

        service.updateUserDetails(updatedUser);

        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).save(user1);

        assertEquals("John", user1.getUname());
        assertEquals("john@example.com", user1.getEmail());
    }

    @Test
    void testUpdateUserDetails_UserNotFound() {
        User updatedUser = new User(9, "Hari", "hari@example.com");

        when(repo.findById(9)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.updateUserDetails(updatedUser));
        verify(repo, times(1)).findById(9);
        verify(repo, never()).save(any(User.class));
    }
}
