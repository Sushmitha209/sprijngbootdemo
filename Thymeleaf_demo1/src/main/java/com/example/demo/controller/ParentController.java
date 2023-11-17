package com.example.demo.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/register")
	@ResponseBody
	public String register(@ModelAttribute("user") User user) {
		System.out.println(user);
		String encodedPassword=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return "Saved success";
	}
}
