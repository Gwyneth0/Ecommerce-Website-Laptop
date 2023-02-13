package com.poly.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AccountDAO;
import com.poly.entity.Account;
import com.poly.entity.MailModel;
import com.poly.service.AccountService;

import com.poly.service.impl.AccountServiceImpl;
import com.poly.service.impl.MailerService;

@Controller
public class SecurityController {

	@Autowired
	JavaMailSender sender;
	
	@Autowired
	AccountServiceImpl acimpl;
	
	@Autowired
	AccountService aservice;
	
	@Autowired
	MailerService mailer;
	
	@Autowired
	AccountDAO accountDAO;
	
	@RequestMapping("/security/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập!");
		return "security/login";
	}

	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "security/login";
	}

	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "security/login";
	}

	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "security/login";
	}

	@RequestMapping("/security/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
		return "security/login";
	}

	
	@RequestMapping("/security/create/form")
	public String createaccount(Model model) {
		model.addAttribute("message", "Vui lòng đăng ký!");
		return "security/create";
	}


	@RequestMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth2) {
		System.out.println(oauth2);
		acimpl.loginFromOAuth2(oauth2);
		return "forward:/security/login/success";
	}
	
	// Chon cach nhan ma
	@RequestMapping("security/choose_Option/form")
	public String choose_Option(Model model) {
		return "security/chooseOption";
	}
	
	
	@RequestMapping("/email/form")
	public String email(Model model) {	
		return "security/enter_Email";
	}
	
	@RequestMapping("/phone/form")
	public String phone(Model model) {	
		return "security/check_code_sdt";
	}
	
	String check_code = "";
	Account account = null;
	@RequestMapping("/enter_Email/form")
	public String enter_Email(Model model, @RequestParam("email") String email) {
		account =  accountDAO.getByEmail("%"+email+"%");
		System.out.println(account);
		if(account == null) {
			model.addAttribute("message","Email không tồn tại");
			return "security/enter_Email";
		}else {
			Random generator = new Random();
			int value = generator.nextInt((9999 - 1000) + 1) + 1000;
			String code = String.valueOf(value);
			check_code = code;
			mailer.push(email, "Ma Code", code);
			System.out.println(check_code);
			return "security/check_code";
		}
	}
	
	@RequestMapping("/newpass/form")
	public String enter_code(Model model, @RequestParam("code") String code) {
		System.out.println(check_code + " va " + code);
		if(check_code.equalsIgnoreCase(code) ) {
			return "security/changePass";
		} else {
			model.addAttribute("message","Sai ma code!");
			return "security/check_code";
		}
	}
	
	@RequestMapping("/matkhaumoi/form")
	public String matkhaumoi(Model model) {
		return "security/changePass_Sdt";
	}
	
	@RequestMapping("/change_Pass/form")
	public String change_Pass(Model model,@RequestParam("passnew") String passnew,@RequestParam("confirm") String confirm) {
		if(passnew.equals(confirm)) {
			account.setPassword(passnew);
			accountDAO.save(account);
			return "security/login";
		}else {
			model.addAttribute("message","Mật khẩu deol khớp!");
			return "security/changePass";
		}
	}
	
		
	@RequestMapping("security/enter_Sdt/form")
	public String enter_Sdt(Model model) {
		model.addAttribute("message", "Vui lòng nhập Số Điện Thoại");
		return "security/enter_Sdt";
	}
	
	@RequestMapping("/security/account_detail")
	public String vldcode(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		//System.out.println(username);
		model.addAttribute("account",aservice.findById(username));
		return "security/account_detail";
	}
}
