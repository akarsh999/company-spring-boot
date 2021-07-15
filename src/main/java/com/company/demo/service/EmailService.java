package com.company.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.company.demo.entity.SalaryAudit;

@Service
public class EmailService {
	
	
	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender mailsender;
	
	public void sendMail(String toEmail, SalaryAudit salaryaudit){
		
		SimpleMailMessage message= new SimpleMailMessage();
		message.setFrom("finance@company.com");
		message.setSubject("salary credited");
		message.setText("your salary is credited into your bank account on "+salaryaudit.getDate().toString());
		message.setTo(toEmail);
		
		try {
			mailsender.send(message);
			log.info("mail sent....");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error in sending email....");
			throw e;
		}
		
		
	}
}
