package com.marlabs.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marlabs.security.User;
import com.marlabs.security.UserRepository;

@Controller
public class SecurityTest {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository repo;

	@RequestMapping("/")
	public String logging(Principal principal,ModelMap map) {

		
		System.out.println("Current User is "+principal.getName());
		
		map.addAttribute("user", principal.getName());
		
		return "home.html";
	}

	@RequestMapping("/password")
	@ResponseBody
	public String passEncode() {

		String pass = "Marlabs";

		String encodedPassword = bCryptPasswordEncoder.encode(pass);

		return "PassWord is " + encodedPassword + " and Original is " + pass;

		// return null;
	}
	
	@RequestMapping("/login")
	public String loginPage(ModelMap map) {
		
		System.out.println("SecurityTest.loginPage()");
		map.addAttribute("user", new User());
		
		return "login";
	}
	
	@RequestMapping("/logout1")
	public String logoutPage(Principal principal) {
		
		System.out.println("SecurityTest.logoutPage()");
		
		return "logout";
	}
	
	@RequestMapping(value = "/reg",method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody User u) {
		
		System.out.println(u);
		
		
		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		
		System.out.println(u.getPassword());
		
		System.out.println("Value is "+u);
		
		repo.save(u);
		
		return "Registered Successfuly...";
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public String roleUser() {
		
		return "welcome Mr.User <a href=\"/logout\">LogOut</a>";
	}
	
	@RequestMapping("/admin")
	@ResponseBody
	public String roleAdmin() {
		
		return "welcome Mr.Admin <a href=\"/logout\">LogOut</a>";
	}

}
