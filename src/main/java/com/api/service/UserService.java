package com.api.service;

import java.util.List;

import com.api.dto.UserDto;
import com.api.model.StockDto;
import com.api.model.User;

public interface UserService {

	public User saveUserDetails(User user) ;
	public User getOneUser(Integer id);
	public List<User> getUserDetails();
	public void deleteOneUser(Integer id);
	public void deleteAllUserDetails();
	public void updateUserDetails(User user);
	
	// Inter-service method
    UserDto getUserWithStocks(Integer uid);

    // Optional: fetch one stock for user
    Object getStockForUser(Integer sid);
}
