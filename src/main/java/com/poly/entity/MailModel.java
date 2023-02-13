package com.poly.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class MailModel {
	String from = "tranquocthang972002@gmail.com";
	String to;
	String subject;
	String body;
	List<String> cc = new ArrayList<>();
	List<String> bcc = new ArrayList<>();
	List<File> files = new ArrayList<>();
	
	public MailModel(String to, String subject, String body) {
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	
}
