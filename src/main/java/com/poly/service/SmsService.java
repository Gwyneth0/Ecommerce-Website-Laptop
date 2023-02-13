package com.poly.service;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.poly.entity.SmsPojo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SmsService {

	    
//	    private final String ACCOUNT_SID ="ACcaff09b5d65b4129579b04b4927cdc9e";
//
//	    private final String AUTH_TOKEN = "346ec4155523b67ad1dde3853ae3e872";
//
//	    private final String FROM_NUMBER = "+15617823498";	
	// sms hao dung sdt hao
	private final String ACCOUNT_SID = "ACe757846ec1cafa009298c70dd63ae87f";

	private final String AUTH_TOKEN = "fe0a5f834823e21ef505430e1367907e";
	private final String FROM_NUMBER = "+18575973684";


	    public void send(SmsPojo sms) {
	    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
	                .create();
	        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction

	    }

	    public void receive(MultiValueMap<String, String> smscallback) {
	    }
	
}
