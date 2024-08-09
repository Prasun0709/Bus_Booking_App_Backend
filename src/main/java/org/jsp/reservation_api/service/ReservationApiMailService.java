package org.jsp.reservation_api.service;

import org.jsp.reservation_api.dto.EmailConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class ReservationApiMailService {
	@Autowired(required = true)
	private JavaMailSender javaMailSender;

	public String sendMail(EmailConfiguration emailConfiguration) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(emailConfiguration.getToAddress());
		simpleMailMessage.setText(emailConfiguration.getText());
		simpleMailMessage.setSubject(emailConfiguration.getSubject());
		javaMailSender.send(simpleMailMessage);
		return "Registration succesfull and Verification mail has been sent";
	}
}
