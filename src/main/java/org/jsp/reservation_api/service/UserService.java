package org.jsp.reservation_api.service;

import java.util.Optional;

import org.jsp.reservation_api.Exception.UserNotFoundException;
import org.jsp.reservation_api.dao.UserDao;
import org.jsp.reservation_api.dto.ResponceStructure;
import org.jsp.reservation_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class UserService {
	@Autowired(required = true)
	private UserDao userdao;
	public ResponseEntity<ResponceStructure<User>> saveUser(User user){
		ResponceStructure<User> structure=new ResponceStructure<>();
		structure.setMessage("UserSaved");
		structure.setData(userdao.saveUser(user));
		structure.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
		
	}
	public ResponseEntity<ResponceStructure<User>> update(User user) {
		Optional<User> recUser = userdao.findById(user.getId());
		ResponceStructure<User> structure = new ResponceStructure<>();
		if (recUser.isPresent()) {
			User dbUser = recUser.get();
			dbUser.setEmail(user.getEmail());
			dbUser.setName(user.getName());
			dbUser.setGst_number(user.getGst_number());
			dbUser.setPassword(user.getPassword());
			dbUser.setPhone(user.getPhone());
			dbUser.setTravels_name(user.getTravels_name());
			structure.setData(userdao.saveUser(user));
			structure.setMessage("User Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new UserNotFoundException("Cannot Update User as Id is Invalid");
	}

	public ResponseEntity<ResponceStructure<User>> findById(int id) {
		ResponceStructure<User> structure = new ResponceStructure<>();
		Optional<User> dbUser = userdao.findById(id);
		if (dbUser.isPresent()) {
			structure.setData(dbUser.get());
			structure.setMessage("User Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid User Id");
	}

	public ResponseEntity<ResponceStructure<User>> verify(long phone, String password) {
		ResponceStructure<User> structure = new ResponceStructure<>();
		Optional<User> dbUser = userdao.verify(phone, password);
		if (dbUser.isPresent()) {
			structure.setData(dbUser.get());
			structure.setMessage("Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid Phone Number or Password");
	}

	public ResponseEntity<ResponceStructure<User>> verify(String email, String password) {
		ResponceStructure<User> structure = new ResponceStructure<>();
		Optional<User> dbUser = userdao.verify(email, password);
		if (dbUser.isPresent()) {
			structure.setData(dbUser.get());
			structure.setMessage("Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Invalid Email Id or Password");
	}

	public ResponseEntity<ResponceStructure<String>> delete(int id) {
		ResponceStructure<String> structure = new ResponceStructure<>();
		Optional<User> dbUser = userdao.findById(id);
		if (dbUser.isPresent()) {
			userdao.delete(id);
			structure.setData("User Found");
			structure.setMessage("User deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new UserNotFoundException("Cannot delete User as Id is Invalid");
	}
}
