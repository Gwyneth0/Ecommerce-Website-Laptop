package com.poly.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.poly.entity.MailModel;


@Service
public class MailerService {
	
	@Autowired
	JavaMailSender sender;
	List<MimeMessage> queue = new ArrayList<>();
	
	public void push(String to, String subject, String body) {
		MailModel mail = new MailModel(to, subject, body);
		this.push(mail);
	}

	public void push(MailModel mail) {
		System.out.println(mail.toString());
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom("tranquocthang972002@gmail.com");
			helper.setTo(mail.getTo());
		    helper.setSubject(mail.getSubject());
		    helper.setText(mail.getBody(), true);
		    helper.setReplyTo(mail.getFrom());
		    
		    //dù cc hay bcc thì chỉ có ng gửi mới xem đc ds ng nhận
		    for (String email: mail.getCc()) {// cc là gửi và cho ng nhận xem những người đc gửi
				helper.addCc(email);
			}
		    
		    for (String email: mail.getBcc()) {// bcc cũng z nhưng k xem đc nhưng ng đc gửi
				helper.addBcc(email);
			}
		    
		    for (File file : mail.getFiles()) {
				helper.addAttachment(file.getName(), file);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		queue.add(message);
		run();
	}
	
	@Scheduled(fixedDelay = 1000)
	public void run() {
		int success = 0;
		int error = 0;
		while (!queue.isEmpty()) {
			MimeMessage message = queue.remove(0);//Lấy MimeMessage từ queue
			try {
				sender.send(message);
				success++;
			} catch (Exception e) {
				error++;
			}
		}
		System.out.printf(">>Sent: %d, Error: %d\r\n", success, error);
	}
}
