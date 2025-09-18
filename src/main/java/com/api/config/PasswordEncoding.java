package com.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoding {
	/*
	 * alice123 -> $2a$10$O0ZYHs4kFcY0zvXyl2EFwu5WKlifoLDNHYmFZItx5AW23j4H8WBVq
	 * bob123 -> $2a$10$2UPHCdchv93r2JbVOe3/BupaMvDS38gfKlgs4MdE.ZgyBIES0Z5Vm
	 * charlie123 -> $2a$10$o99WBb8kNtEqWK2tTpIA9O2kQh0NFfgZyoJXzs1/w5vhwJmJ38ZNa
	 * david123 -> $2a$10$ukt2GlhEIVmv2ocvFUaNY.MJNxshv2VA18ehBvi6paRAxxD3N4gTq
	 * john123 -> $2a$10$DHoVnxhifKjU5RNcBrHLGusjBvh78zOzO5DtWRHVCDZEsx7coTrJ2
	 * $2a$10$bumxSJtfSVa2ana2zLQHw.uqMFFHyLkCDe7rp4AD5ejtKpetrtsp.s
	 * 
	 */
	 public static void main(String[] args) {
	        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // initialize
	        String rawPassword = "renu123";
	        String encodedPassword = passwordEncoder.encode(rawPassword);
	        System.out.println("Encoded password: " + encodedPassword);
	    }

}
