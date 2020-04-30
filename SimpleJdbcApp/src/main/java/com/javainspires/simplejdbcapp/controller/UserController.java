package com.javainspires.simplejdbcapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javainspires.simplejdbcapp.form.LoginForm;
import com.javainspires.simplejdbcapp.repository.UserRepository;

import java.util.List;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/create")
	public String create(@ModelAttribute LoginForm data) {
		int response = userRepository.createUser(data);
		String result;
		if(response==1){
			result="<br>Data created<br>";
		}
		else{
			result="<br>Unable to create data, Make sure the data is in correct format<br>";
		}
		result+="<br><br>Click <a href='billing.html'>here</a> to go to Billing page";
		result+="<br><br>Click <a href='login.html'>here</a> to go to Creation page";
		return result;
	}
	
	@GetMapping("/products")
	public List<LoginForm> check(@RequestParam String key) {
		return userRepository.getProducts(key);
	}
	
	@PostMapping("/billing")
	public List<LoginForm> check(@RequestBody int[] codes) {
		return userRepository.getProductList(codes);
	}

}

