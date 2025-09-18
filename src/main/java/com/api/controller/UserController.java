package com.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.StockDto;
import com.api.model.User;
import com.api.service.StockServiceClient;
import com.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "User management endpoints")
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    
    @Autowired
    private StockServiceClient stockServiceClient;

    @GetMapping("/stock/{sid}")
    @Operation(summary = "Fetching the stock details based on userid")
    public Object getStockForUser(@PathVariable("sid") Integer sid) {
        return service.getStockForUser(sid);
    }
   /*
    @GetMapping("/{uid}/stocks")
    public ResponseEntity<List<StockDto>> getUserStocks(@PathVariable Integer uid) {
        List<StockDto> stocks = service.getStockForUser(uid);
        return ResponseEntity.ok(stocks);
    }*/
    @Operation(summary = "Add new user details")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addingUserDetails(@RequestBody User user) {
        logger.info("Request received to add user: {}", user);
        User savedUser = service.saveUserDetails(user);
        logger.info("User added successfully with ID: {}", savedUser.getUid());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary = "Fetching one user details based on ID")
    @GetMapping("/get/{uid}")
    public ResponseEntity<User> fetchUserDetails(@PathVariable("uid") Integer uid) {
        logger.info("Request received to fetch user with ID: {}", uid);
        User user = service.getOneUser(uid);
        logger.info("User fetched successfully: {}", user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "Fetching all user details")
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> fetchAllUserDetails() {
        logger.info("Request received to fetch all users");
        List<User> users = service.getUserDetails();
        logger.debug("Number of users fetched: {}", users.size());
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Operation(summary = "Deleting one user details based on ID")
    @DeleteMapping("/delete/{uid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> deleteOneUser(@PathVariable("uid") Integer uid) {
        logger.info("Request received to delete user with ID: {}", uid);
        service.deleteOneUser(uid);
        logger.info("User deleted successfully with ID: {}", uid);
        return ResponseEntity.status(HttpStatus.OK).body(uid);
    }

    @Operation(summary = "Deleting all user details")
    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAllUsers() {
        logger.warn("Request received to delete all users");
        service.deleteAllUserDetails();
        logger.info("All users deleted successfully");
        return ResponseEntity.ok("All records deleted successfully.");
    }

    @Operation(summary = "Updating user details")
    @PutMapping("/update")
    public ResponseEntity<String> updateUserDetails(@RequestBody User user) {
        logger.info("Request received to update user: {}", user);
        service.updateUserDetails(user);
        logger.info("User updated successfully with ID: {}", user.getUid());
        return ResponseEntity.ok("Record updated successfully.");
    }
}
