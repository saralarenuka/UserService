package com.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.controller.UserController;
import com.api.model.User;
import com.api.service.UserService;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService service;

	@InjectMocks
	private UserController controller;
	
	private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); 

        user1 = new User();
        user1.setUid(1);
        user1.setUname("Alice");
        user1.setEmail("alice@example.com");

        user2 = new User();
        user2.setUid(2);
        user2.setUname("Bob");
        user2.setEmail("bob@example.com");
    }
	
    @Test
	void testAddingUserDetails() {
		when(service.saveUserDetails(user1)).thenReturn(user1);
		ResponseEntity<User> response = controller.addingUserDetails(user1);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user1, response.getBody());
		
		verify(service, times(1)).saveUserDetails(user1);
	}
	
	@Test
    void testFetchOneUserDetails() {
    	when(service.getOneUser(user1.getUid())).thenReturn(user1);
    	ResponseEntity<User> response = controller.fetchUserDetails(user1.getUid());
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(user1, response.getBody());
    	verify(service, times(1)).getOneUser(user1.getUid());
    }
	
    @Test
    void testFetchAllUserDetails() {
    	 List<User> userList = new ArrayList<>();
    	 userList.add(user1);
    	 userList.add(user2);
         when(service.getUserDetails()).thenReturn(userList);

         ResponseEntity<List<User>> response = controller.fetchAllUserDetails();

         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(2, response.getBody().size());
         assertEquals(userList, response.getBody());

         verify(service, times(1)).getUserDetails();
    }
    
    @Test
    void testDeleteOneUserDetails() {
    	 doNothing().when(service).deleteOneUser(1);
         ResponseEntity<Integer> response = controller.deleteOneUser(1);

         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertEquals(1, response.getBody());

         verify(service, times(1)).deleteOneUser(1);
    }
    
    @Test
    void testDeleteAllRecords() {
    	doNothing().when(service).deleteAllUserDetails();
    	ResponseEntity<String> response = controller.deleteAllUsers();
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals("All records deleted successfully.", response.getBody());
    	
    	verify(service,times(1)).deleteAllUserDetails();
    }
    
    @Test
    void testUpdateUserDetails() {
    	doNothing().when(service).updateUserDetails(user1);
    	ResponseEntity<String> res = controller.updateUserDetails(user1);
    	assertEquals(HttpStatus.OK, res.getStatusCode());
    	assertEquals("Record updated successfully.", res.getBody());
    	
    	verify(service,times(1)).updateUserDetails(user1);
    }
    


    
}
