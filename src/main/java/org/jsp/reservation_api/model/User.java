package org.jsp.reservation_api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false , unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private long phone;
	@Column(nullable = false)
	private String gender;
	@Column(nullable = false)
	private int age;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private int Gst_number;
	@Column(nullable = false)
	private String Travels_name;
	@Column(unique = true)
	private String token;
	@Column(nullable = false)
	private String status;
	@OneToMany(mappedBy = "user")
	private List<Ticket> tickets;
}
