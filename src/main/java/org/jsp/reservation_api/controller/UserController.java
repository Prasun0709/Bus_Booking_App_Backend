package org.jsp.reservation_api.controller;

import org.jsp.reservation_api.dto.ResponceStructure;
import org.jsp.reservation_api.model.User;
import org.jsp.reservation_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired(required = true)
	private UserService userservice;
	
	@PostMapping
	public ResponseEntity<ResponceStructure<User>> saveUser(@RequestBody User user){
		return userservice.saveUser(user);
	}
	@PutMapping
	public ResponseEntity<ResponceStructure<User>> updateUser(@RequestBody User user) {
		return userservice.update(user);
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponceStructure<User>> saveUser(@PathVariable int id) {
		return userservice.findById(id);
	}

	@PostMapping("/verify-by-phone")
	public ResponseEntity<ResponceStructure<User>> verify(@RequestParam long phone, @RequestParam String password) {
		return userservice.verify(phone, password);
	}

	@PostMapping("/verify-by-email")
	public ResponseEntity<ResponceStructure<User>> verify(@RequestParam String email, @RequestParam String password) {
		return userservice.verify(email, password);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponceStructure<String>> delete(@PathVariable int id) {
		return userservice.delete(id);
	}
}
