package org.jsp.reservation_api.dao;

import java.util.Optional;

import org.jsp.reservation_api.model.User;
import org.jsp.reservation_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class UserDao {
	@Autowired(required = true)
	private UserRepository userrepository;
	
	public User saveUser(User user) {
		return userrepository.save(user);
	}
	public Optional<User> findById(int id) {
		return userrepository.findById(id);
	}

	public Optional<User> verify(long phone, String password) {
		return userrepository.findByPhoneAndPassword(phone, password);
	}

	public Optional<User> verify(String email, String password) {
		return userrepository.findByEmailAndPassword(email, password);
	}

	public void delete(int id) {
		userrepository.deleteById(id);
	}
}
