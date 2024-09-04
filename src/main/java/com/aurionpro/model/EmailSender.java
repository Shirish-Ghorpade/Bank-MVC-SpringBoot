package com.aurionpro.model;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	private final JavaMailSender mailSender;

	public EmailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

///////////////////////////////////////////////////////////
//	To send the simple mail
	public String sendEmail(String subject, String body, String emailReceiver) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();

			message.setFrom("shirish9191@gmail.com");
			message.setTo(emailReceiver);
			message.setSubject(subject);
			message.setText(body);
			mailSender.send(message);
			return "Success";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
