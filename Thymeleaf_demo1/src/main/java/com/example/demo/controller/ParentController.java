package com.example.demo.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class ParentController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")

	public String display() {
		return "home";
	}
	
	//Registration page
	@GetMapping("/registration")
	public String registrationPage(User user) {
		return "registrationPage";
	}
	
	@GetMapping("/loginPage")
	public String loginPage(User user) {
		return "loginPage";
	}
	
	@PostMapping("/register")
	@ResponseBody
	public String register(@ModelAttribute("user") User user) {
		System.out.println(user);
		String encodedPassword=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return "Saved success";
	}
	
	
	@PostMapping("/login")
	@ResponseBody
	public String loginProcess(@RequestParam("username")String username,@RequestParam("password")String password) {
		
		User dbUser=userRepository.findByUsername(username);
		Boolean isPasswordMatch=BCrypt.checkpw(password, dbUser.getPassword());
		if(isPasswordMatch)
			return "welcome user";
		else
			return "Failed to login";
	}
}
