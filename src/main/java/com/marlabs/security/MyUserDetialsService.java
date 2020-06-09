package com.marlabs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetialsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println(username);
		User user=repo.findByName(username);
		System.out.println(user);
		if(user==null) {
			System.out.println(user);
			System.out.println("this is code is executed....");
			throw new UsernameNotFoundException("User Not Founded...");
		}
		System.out.println(user);
		
		return new UserPrinceple(user);
	}

}
