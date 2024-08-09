package org.jsp.reservation_api.service;

import java.util.Optional;

import org.jsp.reservation_api.Exception.AdminNotFoundException;
import org.jsp.reservation_api.dao.AdminDao;
import org.jsp.reservation_api.dto.AdminRequest;
import org.jsp.reservation_api.dto.AdminResponse;
import org.jsp.reservation_api.dto.EmailConfiguration;
import org.jsp.reservation_api.dto.ResponceStructure;
import org.jsp.reservation_api.model.Admin;
import org.jsp.reservation_api.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ReservationApiMailService mailService;
	
	@Autowired
	private EmailConfiguration emailConfiguration;
	
	@Autowired
	private LinkGeneratorService linkGeneratorService;
	

	public ResponseEntity<ResponceStructure<AdminResponse>> saveAdmin(AdminRequest adminRequest, HttpServletRequest request) {
		ResponceStructure<AdminResponse> structure = new ResponceStructure<>();
		Admin admin = mapToAdmin(adminRequest);
		admin.setStatus(AccountStatus.IN_ACTIVE.toString());
		admin=adminDao.saveAdmin(admin);
		String activation_link=linkGeneratorService.getActivationLink(admin, request);
		emailConfiguration.setSubject("Activate Yout Account");
		emailConfiguration.setText("Dear Admin Please Activate your Account by clicking on the following link:"+ activation_link);
		emailConfiguration.setToAddress(admin.getEmail());
		structure.setMessage(mailService.sendMail(emailConfiguration));
		structure.setData(mapToAdminResponse(admin));
		structure.setStatusCode(HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);

	}

	public ResponseEntity<ResponceStructure<Admin>> update(AdminRequest adminRequest,int id) {
		Optional<Admin> recAdmin = adminDao.findById(id);
		ResponceStructure<Admin> structure = new ResponceStructure<>();
		if (recAdmin.isPresent()) {
			Admin dbAdmin = mapToAdmin(adminRequest);
			dbAdmin.setId(id);
			structure.setData(adminDao.saveAdmin(dbAdmin));
			structure.setMessage("Admin Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(structure);
		}
		throw new AdminNotFoundException("Cannot Update Admin as Id is Invalid");
	}

	public ResponseEntity<ResponceStructure<Admin>> findById(int id) {
		ResponceStructure<Admin> structure = new ResponceStructure<>();
		Optional<Admin> dbAdmin = adminDao.findById(id);
		if (dbAdmin.isPresent()) {
			structure.setData(dbAdmin.get());
			structure.setMessage("Admin Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Admin Id");
	}

	public ResponseEntity<ResponceStructure<Admin>> verify(long phone, String password) {
		ResponceStructure<Admin> structure = new ResponceStructure<>();
		Optional<Admin> dbAdmin = adminDao.verify(phone, password);
		if (dbAdmin.isPresent()) {
			structure.setData(dbAdmin.get());
			structure.setMessage("Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Phone Number or Password");
	}

	public ResponseEntity<ResponceStructure<Admin>> verify(String email, String password) {
		ResponceStructure<Admin> structure = new ResponceStructure<>();
		Optional<Admin> dbAdmin = adminDao.verify(email, password);
		if (dbAdmin.isPresent()) {
			structure.setData(dbAdmin.get());
			structure.setMessage("Verification Succesfull");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Invalid Email Id or Password");
	}

	public ResponseEntity<ResponceStructure<String>> delete(int id) {
		ResponceStructure<String> structure = new ResponceStructure<>();
		Optional<Admin> dbAdmin = adminDao.findById(id);
		if (dbAdmin.isPresent()) {
			adminDao.delete(id);
			structure.setData("Admin Found");
			structure.setMessage("Admin deleted");
			structure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(structure);
		}
		throw new AdminNotFoundException("Cannot delete Admin as Id is Invalid");
	}

	private Admin mapToAdmin(AdminRequest adminRequest) {
		return Admin.builder().email(adminRequest.getEmail()).name(adminRequest.getName())
				.phone(adminRequest.getPhone()).password(adminRequest.getPassword())
				.gst_number(adminRequest.getGst_number()).travels_name(adminRequest.getTravels_name()).build();
	}
	
	private AdminResponse mapToAdminResponse(Admin admin) {
		return AdminResponse.builder().name(admin.getName()).email(admin.getEmail()).id(admin.getId())
				.gst_number(admin.getGst_number()).phone(admin.getPhone()).travels_name(admin.getTravels_name())
				.password(admin.getPassword()).build();
	}

	public String activate(String token) {
		Optional<Admin> recAdmin=adminDao.findByToken(token);
		if(recAdmin.isEmpty())
			throw new AdminNotFoundException("Invalid Token");
		Admin dbAdmin=recAdmin.get();
		dbAdmin.setStatus("ACTIVE");
		adminDao.saveAdmin(dbAdmin);
		return "Your Account has been activated";
	}
	public String forgotPassword(String email, HttpServletRequest request) {
		Optional<Admin> recAdmin =adminDao.findByEmail(email);
		if(recAdmin.isEmpty()) {
			throw new AdminNotFoundException("Inavalid Email ID");
		}
			Admin admin=recAdmin.get();
			String resetPasswordLink=linkGeneratorService.getResetPasswordLink(admin, request);
			emailConfiguration.setToAddress(email);
			emailConfiguration.setText("Please Click on the following link to reset your password"+ resetPasswordLink);
			emailConfiguration.setSubject("Reset your password");
			mailService.sendMail(emailConfiguration);
			return "reset password link  has been sent to entered mail Id";
	}
	public AdminResponse verifyLink(String token) {
		Optional<Admin> recAdmin= adminDao.findByToken(token);
		if(recAdmin.isEmpty())
			throw new AdminNotFoundException("Link has been Expired or its not valid");
		Admin dbadmin=recAdmin.get();
		dbadmin.setStatus("ACTIVE");
		dbadmin.setToken(null);
		adminDao.saveAdmin(dbadmin);
		return mapToAdminResponse(dbadmin);
	}
}
