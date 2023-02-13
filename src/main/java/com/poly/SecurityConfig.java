package com.poly;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poly.entity.Account;
import com.poly.service.AccountService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	//Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Account user = accountService.findById(username);
				String password = pe.encode(user.getPassword());
				String []roles = user.getAuthorities().stream()
									 .map(er -> er.getRole().getId())
									 .collect(Collectors.toList()).toArray(new String[0]);
				return User.withUsername(username).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + "not found!");
			}
		});
	}
	
	//Phân quyền sử dụng
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/order/**").authenticated()
		.antMatchers("/admin/**").hasAnyRole("ADMIN", "STAFF")
		.antMatchers("/rest/roleIsDeleted", "/ad/rest/accountAdmin", "/rest/autho").hasAnyRole("ADMIN")
			.anyRequest().permitAll();
		
		http.formLogin()
			.loginPage("/security/login/form")
			.loginProcessingUrl("/security/login")
			.defaultSuccessUrl("/security/login/success", false)
			.failureUrl("/security/login/error");
		
		http.rememberMe()
			.tokenValiditySeconds(86400);
		
		http.exceptionHandling()// truy cập đến địa chỉ chưa đc cấp quyền
			.accessDeniedPage("/security/unauthoried");
		// dang xuat
		http.logout()
			.logoutUrl("/security/logoff")
			.logoutSuccessUrl("/security/logoff/success");
		
		
		// OAuth2 - Đăng nhập bằng mạng xã hội
		http.oauth2Login()
			.loginPage("/security/login/form")
			.defaultSuccessUrl("/oauth2/login/success", true) // lúc nào thành công cũng vào trang này
			.failureUrl("/security/login/error")
			.authorizationEndpoint() // <a href="/oauth2/authorization/google">Google</a>
				.baseUri("/oauth2/authorization");
	}	
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
