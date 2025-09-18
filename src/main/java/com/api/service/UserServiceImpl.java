package com.api.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.dto.UserDto;
import com.api.exception.UserNotFoundException;
import com.api.model.StockDto;
import com.api.model.User;
import com.api.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	 private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private RestTemplate restTemplate;
  	
	 @Value("${stock.service.url}")
	    private String stockServiceUrl; // e.g., http://localhost:8081/stock/user/

	    @Override
	    public UserDto getUserWithStocks(Integer uid) {
	        // Fetch user from DB
	        User user = repo.findById(uid)
	                .orElseThrow(() -> new UserNotFoundException("User not found with id " + uid));

	        // Call StockService to get stocks
	        ResponseEntity<StockDto[]> response = restTemplate.getForEntity(
	                stockServiceUrl + uid, StockDto[].class);

	        List<StockDto> stocks = response.getBody() != null ? List.of(response.getBody()) : List.of();

	        // Build UserDto
	        UserDto dto = new UserDto();
	        dto.setUid(user.getUid());
	        dto.setUname(user.getUname());
	        dto.setEmail(user.getEmail());
	        dto.setRole(user.getRole());
	        dto.setCreatedDate(user.getCreatedDate());
	        dto.setModifiedDate(user.getModifiedDate());
	        dto.setStocks(stocks);

	        return dto;
	    }

	    @Override
	    public Object getStockForUser(Integer uid) {
	        String url = "http://localhost:8081/stock/getById/" + uid;
	        return restTemplate.getForObject(url, Object.class);
	           
	    }
	
	@Override
	public User saveUserDetails(User user) {		
		 logger.info("Saving user: {}", user);
		 User savedUser = repo.save(user);
		 logger.info("User saved successfully with ID: {}", savedUser.getUid());
		return savedUser;
	}
	
	@Override
	public User getOneUser(Integer id) {
		   logger.info("Fetching user with ID: {}", id);
		return repo.findById(id).orElseThrow(() -> {
                logger.error("User with ID {} not found", id);
                return new UserNotFoundException("User with ID " + id + " not found in Database");
            });
}

	@Override
	public List<User> getUserDetails() {
		logger.info("Fetching all users from database");
        List<User> users = repo.findAll();
        logger.debug("Number of users fetched: {}", users.size());
        return users;
	}

	@Override
	public void deleteOneUser(Integer id) {
		 logger.info("Deleting user with ID: {}", id);
	        repo.deleteById(id);
	        logger.info("User deleted successfully with ID: {}", id);
	}

	@Override
	public void deleteAllUserDetails() {
		 logger.warn("Deleting all user records!!");
	        repo.deleteAll();
	        logger.info("All users deleted successfully");
	}
	
	@Override
	public void updateUserDetails(User user) {
		 logger.info("Updating user with ID: {}", user.getUid());
	        User userExists = repo.findById(user.getUid())
	                              .orElseThrow(() -> {
	                                  logger.error("User not found with ID: {}", user.getUid());
	                                  return new UserNotFoundException("Record not available in DB with ID " + user.getUid());
	                              });
	        userExists.setUname(user.getUname());
	        userExists.setEmail(user.getEmail());
	        repo.save(userExists);
	        logger.info("User updated successfully with ID: {}", user.getUid());
	    }
	
	}
